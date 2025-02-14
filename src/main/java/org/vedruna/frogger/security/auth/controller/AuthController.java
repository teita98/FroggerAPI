package org.vedruna.frogger.security.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;
import org.vedruna.frogger.dto.ResponseDTO;
import org.vedruna.frogger.dto.UserDTO;
import org.vedruna.frogger.security.auth.dto.AuthResponseDTO;
import org.vedruna.frogger.security.auth.dto.LoginRequestDTO;
import org.vedruna.frogger.security.auth.dto.RegisterRequestDTO;
import org.vedruna.frogger.security.auth.service.AuthServiceI;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private AuthServiceI authService;


    @PostMapping(value = "/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequestDTO request) {
        if (request.getUsername() == null || request.getPassword() == null || request.getUsername().isEmpty() || request.getPassword().isEmpty()) {
            return ResponseEntity.badRequest().body(new AuthResponseDTO("Falta usuario o contraseña."));
        }
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping(value = "/register")
    public ResponseEntity<ResponseDTO> register(@RequestBody RegisterRequestDTO request) {
        authService.register(request);
        return ResponseEntity.ok(new ResponseDTO("User registered successfully"));
    }

}
