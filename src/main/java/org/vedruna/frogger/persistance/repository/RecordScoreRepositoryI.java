package org.vedruna.frogger.persistance.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.vedruna.frogger.persistance.model.RecordScore;
import org.vedruna.frogger.persistance.model.User;
import java.util.Optional;

@Repository
public interface RecordScoreRepositoryI extends JpaRepository<RecordScore, Integer> {
    @Query("SELECT r FROM RecordScore r WHERE r.user.userId = :userId")
    Optional<RecordScore> findByUserId(@Param("userId") Integer userId); // Busca por id
    Optional<RecordScore> findByUser(User user); // Busca por usuario

}
