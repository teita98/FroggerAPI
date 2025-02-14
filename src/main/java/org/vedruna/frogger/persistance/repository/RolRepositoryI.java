package org.vedruna.frogger.persistance.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vedruna.frogger.persistance.model.Rol;

@Repository
public interface RolRepositoryI extends JpaRepository<Rol, Integer> {
    Optional<Rol> findByRolName(String rolName);
}
