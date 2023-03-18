package ztpai.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ztpai.models.Role.Role;
import ztpai.models.Role.RoleEnum;

import java.util.Optional;

public interface RoleRepository extends JpaRepository <Role, Long> {
    Role findByName(RoleEnum name);
}
