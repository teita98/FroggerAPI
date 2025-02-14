package org.vedruna.frogger.controller.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vedruna.frogger.dto.UserDTO;
import org.vedruna.frogger.service.FollowServiceI;

@RestController
@RequestMapping("/api/v1/user")
public class FollowController {
    @Autowired
    private FollowServiceI followService;

    /**
     * POST /follow/{userId} - El usuario logueado sigue al usuario indicado.
     */
    @PostMapping("/follow/{userId}")
    public ResponseEntity<String> followUser(@PathVariable Integer userId) {
        followService.followUser(userId);
        return ResponseEntity.ok("Ahora sigues al usuario " + userId);
    }

    /**
     * DELETE /unfollow/{userId} - El usuario logueado deja de seguir al usuario indicado.
     */
    @DeleteMapping("/unfollow/{userId}")
    public ResponseEntity<String> unfollowUser(@PathVariable Integer userId) {
        followService.unfollowUser(userId);
        return ResponseEntity.ok("Has dejado de seguir al usuario " + userId);
    }

    /**
     * GET /follow/{userId} - Devuelve en forma paginada los usuarios a los que sigue el usuario con id userId.
     */
    @GetMapping("/follow/{userId}")
    public ResponseEntity<Page<UserDTO>> getFollowing(@PathVariable Integer userId, Pageable pageable) {
        return ResponseEntity.ok(followService.getFollowing(userId, pageable));
    }

    /**
     * GET /followers/{userId} - Devuelve en forma paginada los seguidores del usuario con id userId.
     */
    @GetMapping("/followers/{userId}")
    public ResponseEntity<Page<UserDTO>> getFollowers(@PathVariable Integer userId, Pageable pageable) {
        return ResponseEntity.ok(followService.getFollowers(userId, pageable));
    }
}
