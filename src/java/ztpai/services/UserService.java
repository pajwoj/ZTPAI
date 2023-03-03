package ztpai.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ztpai.models.User;
import ztpai.repositories.UserRepository;

@Service
public class UserService {
    private UserRepository repository;

    @Autowired
    public void setRepository(UserRepository repository) {
        this.repository = repository;
    }

    public User addUser(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        User newUser = new User(
                user.getEmail(),
                encoder.encode(user.getPassword())
        );

        return repository.save(newUser);
    }

    public String test() {
        return "test";
    }
}