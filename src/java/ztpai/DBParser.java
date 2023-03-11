package ztpai;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ztpai.services.RouteService;
import ztpai.services.StationService;
import ztpai.services.TrainService;
import ztpai.services.UserService;

@Component
public class DBParser {
    private RouteService rService;
    private TrainService tService;
    private UserService uService;
    private StationService sService;

    @Autowired
    public void setrService(RouteService rService) {
        this.rService = rService;
    }

    @Autowired
    public void settService(TrainService tService) {
        this.tService = tService;
    }

    @Autowired
    public void setuService(UserService uService) {
        this.uService = uService;
    }

    @Autowired
    public void setsService(StationService sService) {
        this.sService = sService;
    }

    public void parse() {
        sService.updateStations();
    }
}
