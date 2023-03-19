package ztpai.services;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import ztpai.dtos.UserDTO;
import ztpai.models.Role.Role;
import ztpai.models.Role.RoleEnum;
import ztpai.models.User;
import ztpai.repositories.RoleRepository;
import ztpai.repositories.UserRepository;
import ztpai.security.jwt.JWTService;
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

    public ResponseEntity<?> login(UserDTO user, HttpServletResponse res) {
        try {
            User userAttemptingLogin = userRepository.findByEmail(user.getEmail()).get();
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("No user with email" + user.getEmail() + " found.", HttpStatus.BAD_REQUEST);
        }

        User userAttemptingLogin = userRepository.findByEmail(user.getEmail()).get();

        Collection<SimpleGrantedAuthority> authorities = new HashSet<>();
        for(Role role : userAttemptingLogin.getRoles()) authorities.add(new SimpleGrantedAuthority(role.getName()));

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword(), authorities);

        try {
            authenticationManager.authenticate(authentication);
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>("Bad credentials.", HttpStatus.UNAUTHORIZED);
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        String token = jwtService.generateToken(userDetails);

        Cookie jwtCookie = new Cookie("jwt", token);
        res.addCookie(jwtCookie);

        Cookie sessionCookie = new Cookie("JSESSIONID", RequestContextHolder.currentRequestAttributes().getSessionId());
        res.addCookie(sessionCookie);

        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}