package ztpai.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ztpai.dtos.RegistrationDTO;
import ztpai.models.User;
import ztpai.repositories.UserRepository;

@Service
public class UserService {
    private UserRepository repository;

    @Autowired
    public void setRepository(UserRepository repository) {
        this.repository = repository;
    }

    private User addUser(RegistrationDTO user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        User newUser = new User(
                user.getEmail(),
                encoder.encode(user.getPassword())
        );

        return repository.save(newUser);
    }

    public ResponseEntity<String> register(RegistrationDTO user) {
        if(repository.existsByEmail(user.getEmail())) return new ResponseEntity<>("User with email: " + user.getEmail() + " already exists!", HttpStatus.BAD_REQUEST);

        this.addUser(user);
        return new ResponseEntity<>("Registration successful!", HttpStatus.OK);
    }
}