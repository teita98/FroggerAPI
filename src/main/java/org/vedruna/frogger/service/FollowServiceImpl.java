package org.vedruna.frogger.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.vedruna.frogger.dto.UserDTO;
import org.vedruna.frogger.persistance.model.User;
import org.vedruna.frogger.persistance.model.UserFollowsUser;
import org.vedruna.frogger.persistance.repository.RecordScoreRepositoryI;
import org.vedruna.frogger.persistance.repository.UserFollowsRepositoryI;
import org.vedruna.frogger.persistance.repository.UserRepositoryI;

@Service
@Transactional
public class FollowServiceImpl implements FollowServiceI {
    @Autowired
    private UserFollowsRepositoryI followRepository;

    @Autowired
    private UserRepositoryI userRepository;

    @Autowired
    private RecordScoreRepositoryI recordScoreRepository;

    private User getAuthenticatedUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    // Usado para POST /follow/{userId} - FollowController
    @Override
    public void followUser(Integer userId) {
        User follower = getAuthenticatedUser();

        // Validación: no permitir seguirse a sí mismo
        if (follower.getUserId().equals(userId)) {
            throw new RuntimeException("No puedes seguirte a ti mismo");
        }

        User following = userRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Usuario a seguir no encontrado"));

        if (followRepository.existsByFollowerAndFollowing(follower, following)) {
            throw new RuntimeException("Ya sigues a este usuario");
        }
        followRepository.save(new UserFollowsUser(follower, following));
    }

    // Usado para DELETE /unfollow/{userId} - FollowController
    @Override
    public void unfollowUser(Integer userId) {
        User follower = getAuthenticatedUser();
        User following = userRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Usuario a dejar de seguir no encontrado"));
        followRepository.deleteByFollowerAndFollowing(follower, following);
    }

    // Usado para GET /follow/{userId} - FollowController
    @Override
    public Page<UserDTO> getFollowing(Integer userId, Pageable pageable) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Guardo los seguidores y seguidos
        Integer followingCount = followRepository.countByFollower(user);
        Integer followersCount = followRepository.countByFollowing(user);

        // Guardo el recordTime
        String recordTime = recordScoreRepository.findByUser(user)
                .map(score -> score.getRecordTime().toString())
                .orElse("No registrado");

        return followRepository.findByFollower(user, pageable)
                .map(follow -> new UserDTO(
                        follow.getFollowing().getUserId(),
                        follow.getFollowing().getUsername(),
                        follow.getFollowing().getEmail(),
                        followingCount,
                        followersCount,
                        recordTime));
    }

    // Usado para GET /followers/{userId} - FollowController
    @Override
    public Page<UserDTO> getFollowers(Integer userId, Pageable pageable) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Guardo los seguidores y seguidos
        Integer followingCount = followRepository.countByFollower(user);
        Integer followersCount = followRepository.countByFollowing(user);

        // Guardo el recordTime
        String recordTime = recordScoreRepository.findByUser(user)
                .map(score -> score.getRecordTime().toString())
                .orElse("No registrado");

        return followRepository.findByFollowing(user, pageable)
                .map(follow -> new UserDTO(
                        follow.getFollower().getUserId(),
                        follow.getFollower().getUsername(),
                        follow.getFollower().getEmail(),
                        followingCount,
                        followersCount,
                        recordTime));
    }

}