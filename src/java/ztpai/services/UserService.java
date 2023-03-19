package ztpai.services;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ztpai.dtos.UserDTO;
import ztpai.models.Role.Role;
import ztpai.models.Role.RoleEnum;
import ztpai.models.User;
import ztpai.repositories.RoleRepository;
import ztpai.repositories.UserRepository;
import ztpai.security.jwt.JWTService;
import ztpai.services.UserDetails.UserDetailsImplementation;
import ztpai.services.UserDetails.UserDetailsServiceImplementation;

import java.util.*;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTService jwtService;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UserDetailsServiceImplementation userDetailsService;

    private User addUser(UserDTO user) {
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(RoleEnum.ROLE_USER);

        roles.add(userRole);

        User newUser = new User(
                user.getEmail(),
                encoder.encode(user.getPassword()),
                roles
        );

        return userRepository.save(newUser);
    }

    public ResponseEntity<String> register(UserDTO user) {
        if(userRepository.existsByEmail(user.getEmail())) return new ResponseEntity<>("User with email: " + user.getEmail() + " already exists!", HttpStatus.BAD_REQUEST);

        this.addUser(user);
        return new ResponseEntity<>("Registration successful!", HttpStatus.OK);
    }

    public ResponseEntity<?> login(UserDTO user, HttpServletRequest req, HttpServletResponse res) {
        try {
            userRepository.findByEmail(user.getEmail()).get();
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("No user with email " + user.getEmail() + " found.", HttpStatus.BAD_REQUEST);
        }

        if(!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken))
            return new ResponseEntity<>("Already logged in!", HttpStatus.OK);

        User userAttemptingLogin = userRepository.findByEmail(user.getEmail()).get();

        Collection<SimpleGrantedAuthority> authorities = new HashSet<>();
        for(Role role : userAttemptingLogin.getRoles()) authorities.add(new SimpleGrantedAuthority(role.getName()));

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword(), authorities);

        Authentication authentication;

        try {
            authentication = authenticationManager.authenticate(token);
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>("Bad credentials.", HttpStatus.UNAUTHORIZED);
        }

        SecurityContext context = SecurityContextHolder.getContext();
        SecurityContextHolder.setContext(context);
        context.setAuthentication(authentication);

        HttpSession session = req.getSession();
        session.setMaxInactiveInterval(600);
        session.setAttribute("SPRING_SECURITY_CONTEXT", context);

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        String jwtToken = jwtService.generateToken(userDetails);

        Cookie jwtCookie = new Cookie("jwt", jwtToken);
        jwtCookie.setPath("/");
        res.addCookie(jwtCookie);

        session.setAttribute("jwt", jwtToken);

        return new ResponseEntity<>("Successfully logged in!", HttpStatus.OK);
    }

    public ResponseEntity<?> logout(HttpServletRequest req, HttpServletResponse res) throws ServletException {
        var a = SecurityContextHolder.getContext().getAuthentication();
        if(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)
            return new ResponseEntity<>("You are not logged in!", HttpStatus.BAD_REQUEST);

        req.getSession().invalidate();
        req.logout();

        Cookie removeSessionCookie = new Cookie("JSESSIONID", null);
        removeSessionCookie.setMaxAge(0);
        res.addCookie(removeSessionCookie);

        Cookie removeJWTCookie = new Cookie("jwt", null);
        removeJWTCookie.setPath("/");
        removeJWTCookie.setMaxAge(0);
        res.addCookie(removeJWTCookie);

        return new ResponseEntity<>("Succesfully logged out!", HttpStatus.OK);
    }

    public String getCurrentUser() {
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof String) return "Not logged in.";

        UserDetailsImplementation principal = (UserDetailsImplementation) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal.getEmail();
    }
}