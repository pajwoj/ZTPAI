package ztpai;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ztpai.services.RouteService;
import ztpai.services.StationService;
import ztpai.services.TrainService;
import ztpai.services.UserService;

@Component
public class DBParser {
    @Autowired
    private RouteService rService;
    @Autowired
    private TrainService tService;
    @Autowired
    private UserService uService;
    @Autowired
    private StationService sService;

    public void parse() {
        sService.updateStations();
    }
}
