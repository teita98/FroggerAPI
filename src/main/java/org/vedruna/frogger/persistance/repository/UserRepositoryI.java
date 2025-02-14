package org.vedruna.frogger.persistance.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.vedruna.frogger.persistance.model.User;

import java.util.Optional;

@Repository
public interface UserRepositoryI extends JpaRepository<User, Integer> {

    @Query("SELECT u FROM User u WHERE u.userId = :userId")
    Optional<User> findByUserId(@Param("userId") Integer userId); // Busca por id
    Optional<User> findByUsername(String username); // Busca por nombre
    Page<User> findByUsernameStartingWith(String username, Pageable pageable); // Busca por inicio de palabra

}
