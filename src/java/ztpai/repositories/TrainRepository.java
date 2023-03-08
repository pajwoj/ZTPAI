package ztpai.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ztpai.models.Train;

@Repository
public interface TrainRepository extends JpaRepository<Train, Long> { }