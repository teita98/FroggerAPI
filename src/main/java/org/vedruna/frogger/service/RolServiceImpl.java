package org.vedruna.frogger.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.vedruna.frogger.dto.RolDTO;
import org.vedruna.frogger.persistance.model.Rol;
import org.vedruna.frogger.persistance.repository.RolRepositoryI;

@Service
public class RolServiceImpl implements RolServiceI {
    @Autowired
    private RolRepositoryI rolRepository;

    @Override
    public RolDTO selectRolByName(String rolName) {
        Rol rol = rolRepository.findByRolName(rolName)
                .orElseThrow(() -> new EmptyResultDataAccessException("Rol not found", 1));
        return new RolDTO(rol);
    }
}
