package org.vedruna.frogger.persistance.model;

import java.util.List;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "roles")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rol_id", nullable = false)
    private Integer rolId;

    @NotNull
    @NotEmpty
    @Size(max = 45)
    @Column(name = "rol_name", unique = true, nullable = false)
    private String rolName;

    @OneToMany(mappedBy = "userRol", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<User> usersWithThisRol;

    // Getters y Setters

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

    public List<User> getUsersWithThisRol() {
        return usersWithThisRol;
    }

    public void setUsersWithThisRol(List<User> usersWithThisRol) {
        this.usersWithThisRol = usersWithThisRol;
    }
}
