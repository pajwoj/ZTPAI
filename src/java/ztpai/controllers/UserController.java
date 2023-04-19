package ztpai.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ztpai.dtos.UserDTO;
import ztpai.models.User;
import ztpai.services.UserService;

import java.util.Optional;

@RestController
@RequestMapping(path = "api/users")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class UserController {
    @Autowired
    private UserService service;

    @Operation(summary = "Register a new user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "400",
                    description = "User already exists.",
                    content = @Content
            ),

            @ApiResponse(
                    responseCode = "200",
                    description = "User registered successfully.",
                    content = @Content
            )
    })
    @PostMapping(path = "/register")
    public ResponseEntity<?> register(@Parameter(description = "Details of the user trying to register.") @RequestBody UserDTO user) {
        return service.register(user);
    }

    @Operation(summary = "Login to an existing account")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "400",
                    description = "No user with given email found.",
                    content = @Content
            ),

            @ApiResponse(
                    responseCode = "200",
                    description = "User logged successfully, or user already logged in before request.",
                    content = @Content
            ),

            @ApiResponse(
                    responseCode = "401",
                    description = "Wrong password for given email.",
                    content = @Content
            )
    })
    @PostMapping(path = "/login")
    public ResponseEntity<?> login(@Parameter(description = "Details of the user trying to login.") @RequestBody UserDTO user, HttpServletRequest req, HttpServletResponse res) {
        return service.login(user, req, res);
    }

    @Operation(summary = "Logout")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "400",
                    description = "Not logged in.",
                    content = @Content
            ),

            @ApiResponse(
                    responseCode = "200",
                    description = "User logged out successfully.",
                    content = @Content
            )
    })
    @PostMapping(path = "/logout")
    public ResponseEntity<?> logout(HttpServletRequest req, HttpServletResponse res) throws ServletException {
        return service.logout(req, res);
    }
}