package ztpai.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ztpai.models.Station;
import ztpai.repositories.StationRepository;

@Service
public class StationService {
    private StationRepository repository;

    @Autowired
    public void setRepository(StationRepository repository) {
        this.repository = repository;
    }

    public Station addStation(Station station) {
        Station newStation = new Station(
                station.getName()
        );

        return repository.save(newStation);
    }

    public String test() {
        return "STATION TEST";
    }
}