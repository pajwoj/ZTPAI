package ztpai.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ztpai.services.StationService;

@RestController
@RequestMapping(path = "api/stations")
public class StationController {
    private StationService service;

    @Autowired
    public void setService(StationService service) {
        this.service = service;
    }

    @GetMapping(path = "/")
    public String test() {
        return service.test();
    }
}