package ztpai.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ztpai.dtos.RegistrationDTO;
import ztpai.services.UserService;

@RestController
@RequestMapping(path = "api/users")
@CrossOrigin(origins = "*")
public class UserController {
    private UserService service;

    @Autowired
    public void setService(UserService service) {
        this.service = service;
    }

    @PostMapping(path = "/register")
    public ResponseEntity<String> register(@RequestBody RegistrationDTO user) {
        return service.register(user);
    }
}