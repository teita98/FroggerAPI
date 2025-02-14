package org.vedruna.frogger.persistance.model;

import java.util.Collection;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    // Se mapea el rol sin que forme parte de la clave primaria
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Roles_rol_id", nullable = false)
    private Rol userRol;

    @NotNull
    @NotEmpty
    @Size(min = 3, max = 20)
    @Column(name = "username", nullable = false, unique = true, length = 20)
    private String username;

    @NotNull
    @NotEmpty
    @Size(min = 8)
    @Column(name = "password", nullable = false, length = 60, columnDefinition = "CHAR(60)")
    private String password;

    @NotNull
    @NotEmpty
    @Email
    @Size(max = 90)
    @Column(name = "email", nullable = false, unique = true, length = 90)
    private String email;

    // RELACIONES
    // Relación ManyToMany para la funcionalidad de “seguir usuarios”
    @ManyToMany
    @JoinTable(name = "user_follows_user",
            joinColumns = @JoinColumn(name = "user_who_follows", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "user_to_follow", referencedColumnName = "user_id"))
    private List<User> usersIFollow;

    @ManyToMany(mappedBy = "usersIFollow")
    private List<User> followers;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private RecordScore scores;


    // Getters y Setters
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Rol getUserRol() {
        return userRol;
    }

    public void setUserRol(Rol userRol) {
        this.userRol = userRol;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<User> getUsersIFollow() {
        return usersIFollow;
    }

    public void setUsersIFollow(List<User> usersIFollow) {
        this.usersIFollow = usersIFollow;
    }

    public List<User> getFollowers() {
        return followers;
    }

    public void setFollowers(List<User> followers) {
        this.followers = followers;
    }

    public RecordScore getScores() {
        return scores;
    }

    public void setScores(RecordScore scores) {
        this.scores = scores;
        if (scores != null) {
            scores.setUser(this); // Asegura la relación bidireccional
        }
    }

    //De momento vamos a hardcodear estos métodos
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("USER"));
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }


}
