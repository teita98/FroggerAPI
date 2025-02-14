package org.vedruna.frogger.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.vedruna.frogger.dto.UserDTO;
import org.vedruna.frogger.persistance.model.User;
import java.util.List;

public interface UserServiceI {
    UserDTO selectMyUser(User user);
    UserDTO selectUserById(Integer userId);
    UserDTO selectUserByName(String username);
    Page<UserDTO> searchUsersByName(String name, Pageable pageable);

    void deleteUser(Integer id);
    UserDTO convertToUserDTO(User user);
}
