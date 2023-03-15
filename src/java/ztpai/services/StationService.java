package ztpai.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ztpai.models.Station;
import ztpai.repositories.StationRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class StationService {
    private StationRepository repository;

    @Autowired
    public void setRepository(StationRepository repository) {
        this.repository = repository;
    }

    private Station addStation(Station station) {
        Station newStation = new Station(
                station.getName()
        );

        return repository.save(newStation);
    }

    private static class StationRequest {
        public String version;
        public String timestamp;
        public List<Map<String, String>> station;
    }

    public void updateStations() {
        String uri = "https://api.irail.be/stations/?format=json&lang=en";

        RestTemplate template = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();

        String json = template.getForObject(uri, String.class);

        try {
            StationRequest result = mapper.readValue(json, StationRequest.class);

            for(Map<String, String> current : result.station) {
                Station temp = new Station(current.get("name"));

                if(repository.existsByName(temp.getName())) continue;
                else this.addStation(temp);
            }

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    public List<Station> findAll() {
        return repository.findAll();
    }
}