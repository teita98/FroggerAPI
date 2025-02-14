package org.vedruna.frogger.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import org.vedruna.frogger.dto.ResponseDTO;
import org.vedruna.frogger.dto.UserDTO;
import org.vedruna.frogger.persistance.model.User;
import org.vedruna.frogger.security.auth.dto.AuthResponseDTO;
import org.vedruna.frogger.security.auth.dto.LoginRequestDTO;
import org.vedruna.frogger.security.auth.dto.RegisterRequestDTO;
import org.vedruna.frogger.service.UserServiceI;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserServiceI userService;

    /**
     * GET /me - Obtener el perfil del usuario autenticado (nombre, email, número de seguidos y seguidores, tiempo récord).
     */
    @GetMapping("/me")
    public ResponseEntity<UserDTO> getMyUser(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(userService.selectMyUser(user));
    }

    /**
     * GET /search?name= - Devuelve de forma paginada la lista de usuarios cuyo nombre comience por el valor de “name”.
     */
    @GetMapping("/search")
    public ResponseEntity<Page<UserDTO>> searchUsers(@RequestParam("name") String name, Pageable pageable) {
        return ResponseEntity.ok(userService.searchUsersByName(name, pageable));
    }

    /**
     * GET /profile/{name} - Devuelve el perfil de un usuario (por username).
     */
    @GetMapping("/profile/{name}")
    public ResponseEntity<UserDTO> getProfileByName(@PathVariable String name) {
        return ResponseEntity.ok(userService.selectUserByName(name));
    }

    /**
     * GET /{userId} - Devuelve el usuario identificado por su ID.
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Integer userId) {
        return ResponseEntity.ok(userService.selectUserById(userId));
    }

    /**
     * DELETE /{userId} - Elimina el usuario identificado por su ID.
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<ResponseDTO> deleteUser(@PathVariable Integer userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok(new ResponseDTO("Has eliminado al usuario con ID " + userId));

    }
}