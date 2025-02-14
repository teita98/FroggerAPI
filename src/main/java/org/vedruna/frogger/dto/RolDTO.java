package org.vedruna.frogger.dto;

import lombok.*;
import org.vedruna.frogger.persistance.model.Rol;


@Getter
@Setter
public class RolDTO {
    private Integer rolId;
    private String rolName;

    public RolDTO() { }
    public RolDTO(Rol rol) {
        this.rolId = rol.getRolId();
        this.rolName = rol.getRolName();
    }
    public Integer getRolId() {
        return rolId;
    }
    public void setRolId(Integer rolId) {
        this.rolId = rolId;
    }
    public String getRolName() {
        return rolName;
    }
    public void setRolName(String rolName) {
        this.rolName = rolName;
    }
}
