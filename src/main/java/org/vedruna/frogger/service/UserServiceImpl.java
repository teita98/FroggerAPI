package org.vedruna.frogger.service;

import java.util.List;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.vedruna.frogger.dto.UserDTO;
import org.vedruna.frogger.persistance.model.User;
import org.vedruna.frogger.persistance.repository.RecordScoreRepositoryI;
import org.vedruna.frogger.persistance.repository.UserFollowsRepositoryI;
import org.vedruna.frogger.persistance.repository.UserRepositoryI;

@Service
public class UserServiceImpl implements UserServiceI {

    @Autowired
    private UserRepositoryI userRepository;

    @Autowired
    private RecordScoreRepositoryI recordScoreRepository;

    @Autowired
    private UserFollowsRepositoryI userFollowsRepository;

    // Usado para GET /me - UserController
    @Override
    public UserDTO selectMyUser(User user) {
        User u = userRepository.findByUserId(user.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("El usuario con ID " + user.getUserId() + " no existe"));
        return convertToUserDTO(u);
    }

    // Usado para GET /search?name= - UserController
    @Override
    public Page<UserDTO> searchUsersByName(String name, Pageable pageable) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("El parámetro 'name' es obligatorio y no puede estar vacío.");
        }
        Page<User> usersPage = userRepository.findByUsernameStartingWith(name, pageable);
        return usersPage.map(this::convertToUserDTO); // Convierte cada User a UserDTO
    }

    // Usado para GET /profile/{name} - UserController
    @Override
    public UserDTO selectUserByName(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("El usuario " + username + " no existe"));
        return convertToUserDTO(user);
    }

    // Usado para GET /{userId} - UserController
    @Override
    public UserDTO selectUserById(Integer userId) {
        User u = userRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("El usuario con ID " + userId + " no existe"));
        return convertToUserDTO(u);
    }

    // Usado para DELETE /{userId} - UserController
    @Override
    @Transactional
    public void deleteUser(Integer id) {
        User user = userRepository.findByUserId(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
        try {
            // Eliminar relaciones de seguimiento del usuario antes de eliminarlo
            userFollowsRepository.deleteByFollower(user);
            userFollowsRepository.deleteByFollowing(user);

            // Eliminar al usuario
            userRepository.delete(user);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("No se puede eliminar el usuario porque tiene datos relacionados.");
        }
    }

    /**
     * Metodo para formatear la respuesta de los metodos anteriores
     * @param user
     * @return User
     */
    @Override
    public UserDTO convertToUserDTO(User user) {
        // Guardo los seguidores y seguidos
        Integer followingCount = userFollowsRepository.countByFollower(user);
        Integer followersCount = userFollowsRepository.countByFollowing(user);

        // Guardo el recordTime
        String recordTime = recordScoreRepository.findByUser(user)
                .map(score -> score.getRecordTime().toString())
                .orElse("No registrado");

        return new UserDTO(
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                followingCount,
                followersCount,
                recordTime
        );
    }

}
