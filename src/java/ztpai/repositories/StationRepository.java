package ztpai.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ztpai.models.Station;

@Repository
public interface StationRepository extends JpaRepository<Station, Long> { }