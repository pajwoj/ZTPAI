package ztpai.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ztpai.services.RouteService;

@RestController
@RequestMapping(path = "api/routes")
public class RouteController {
    private RouteService service;

    @Autowired
    public void setService(RouteService service) {
        this.service = service;
    }

    @GetMapping(path = "/")
    public String test() {
        return service.test();
    }
}