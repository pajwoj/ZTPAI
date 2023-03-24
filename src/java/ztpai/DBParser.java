package ztpai;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ztpai.services.StationService;

@Component
public class DBParser {
    @Autowired
    private StationService sService;

    public void parse() {
        sService.updateStations();
    }
}
