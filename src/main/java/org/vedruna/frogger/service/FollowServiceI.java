package org.vedruna.frogger.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.vedruna.frogger.dto.UserDTO;
import org.vedruna.frogger.persistance.model.User;

public interface FollowServiceI {
    void followUser(Integer userId);
    void unfollowUser(Integer userId);
    Page<UserDTO> getFollowing(Integer userId, Pageable pageable);
    Page<UserDTO> getFollowers(Integer userId, Pageable pageable);

}
