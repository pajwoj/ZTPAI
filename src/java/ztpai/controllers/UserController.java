package ztpai.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ztpai.dtos.UserDTO;
import ztpai.services.UserService;

@RestController
@RequestMapping(path = "api/users")
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private UserService service;

    @PostMapping(path = "/register")
    public ResponseEntity<?> register(@RequestBody UserDTO user) {
        return service.register(user);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<?> login(@RequestBody UserDTO user, HttpServletRequest req, HttpServletResponse res) {
        return service.login(user, req, res);
    }

    @PostMapping(path = "/logout")
    public ResponseEntity<?> logout(HttpServletRequest req, HttpServletResponse res) throws ServletException {
        return service.logout(req, res);
    }
}