package org.vedruna.frogger.persistance.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalTime;

@Entity
@Table(name = "record_scores")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecordScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_id")
    private Integer recordId;

    // Aunque en la base de datos la PK es compuesta, aquí mapeamos solo record_id como PK.
    // Se impone que cada usuario tenga un único récord marcándolo como UNIQUE.
    @OneToOne
    @JoinColumn(name = "users_user_id", referencedColumnName = "user_id", nullable = false, unique = true)
    private User user;

    @NotNull(message = "El tiempo de la puntuación no puede ser nulo")
    @Column(name = "record_scorescol", nullable = false)
    private LocalTime recordTime;


    // Getters y Setters

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalTime getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(LocalTime recordTime) {
        this.recordTime = recordTime;
    }
}