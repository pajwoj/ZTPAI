package ztpai.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ztpai.models.Station;

import java.util.List;

@Repository
public interface StationRepository extends JpaRepository<Station, Long> {
    boolean existsByName(String name);
    List<Station> findAll();
}