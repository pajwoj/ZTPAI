package ztpai.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ztpai.services.TrainService;

@RestController
@RequestMapping(path = "api/trains")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class TrainController {
    @Autowired
    private TrainService service;

    @GetMapping(path = "/")
    public String test() {
        return service.test();
    }
}