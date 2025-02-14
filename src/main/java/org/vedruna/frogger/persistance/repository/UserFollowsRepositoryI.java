package org.vedruna.frogger.persistance.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.vedruna.frogger.persistance.model.User;
import org.vedruna.frogger.persistance.model.UserFollowsUser;
import org.vedruna.frogger.persistance.model.UserFollowsUserId;

@Repository
public interface UserFollowsRepositoryI extends JpaRepository<UserFollowsUser, UserFollowsUserId> {
    boolean existsByFollowerAndFollowing(User follower, User following);

    @Modifying
    @Transactional
    @Query("DELETE FROM UserFollowsUser u WHERE u.follower = :follower AND u.following = :following")
    void deleteByFollowerAndFollowing(@Param("follower") User follower, @Param("following") User following);

    Page<UserFollowsUser> findByFollower(User follower, Pageable pageable);
    Page<UserFollowsUser> findByFollowing(User following, Pageable pageable);

    @Query("SELECT COUNT(f) FROM UserFollowsUser f WHERE f.follower = :user")
    Integer countByFollower(@Param("user") User user);

    @Query("SELECT COUNT(f) FROM UserFollowsUser f WHERE f.following = :user")
    Integer countByFollowing(@Param("user") User user);

    // Eliminar todas las relaciones donde el usuario es seguidor
    @Transactional
    void deleteByFollower(User user);

    // Eliminar todas las relaciones donde el usuario es seguido
    @Transactional
    void deleteByFollowing(User user);
}
