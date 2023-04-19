package ztpai.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ztpai.services.StationService;

import java.util.List;

@RestController
@RequestMapping(path = "api/stations")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class StationController {
    @Autowired
    private StationService service;

    @Operation(summary = "Get the current station list from irail.be API")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully got the station list.",
                    content = @Content
            )
    })
    @GetMapping(path = "/all")
    public List findAll() {
        return service.findAll();
    }
}