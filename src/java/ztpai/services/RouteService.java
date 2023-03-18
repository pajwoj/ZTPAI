package ztpai.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ztpai.models.Route;
import ztpai.repositories.RouteRepository;

@Service
public class RouteService {
    @Autowired
    private RouteRepository repository;

    public Route addRoute(Route route) {
        Route newRoute = new Route(
                route.getStation(),
                route.getTrain(),
                route.getHour()
        );

        return repository.save(newRoute);
    }

    public String test() {
        return "ROUTE TEST";
    }
}