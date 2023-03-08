package ztpai.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ztpai.models.Route;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> { }