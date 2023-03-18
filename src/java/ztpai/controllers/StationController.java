package ztpai.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ztpai.services.StationService;

import java.util.List;

@RestController
@RequestMapping(path = "api/stations")
@CrossOrigin(origins = "*")
public class StationController {
    @Autowired
    private StationService service;

    @GetMapping(path = "/all")
    public List findAll() {
        return service.findAll();
    }
}