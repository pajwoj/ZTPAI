package ztpai.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ztpai.models.Train;
import ztpai.repositories.TrainRepository;

@Service
public class TrainService {
    @Autowired
    private TrainRepository repository;

    public Train addTrain(Train train) {
        Train newTrain = new Train(
                train.getName()
        );

        return repository.save(newTrain);
    }

    public String test() {
        return "TRAIN TEST";
    }
}