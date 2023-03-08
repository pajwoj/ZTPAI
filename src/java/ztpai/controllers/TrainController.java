package ztpai.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ztpai.services.TrainService;

@RestController
@RequestMapping(path = "api/trains")
public class TrainController {
    private TrainService service;

    @Autowired
    public void setService(TrainService service) {
        this.service = service;
    }

    @GetMapping(path = "/")
    public String test() {
        return service.test();
    }
}