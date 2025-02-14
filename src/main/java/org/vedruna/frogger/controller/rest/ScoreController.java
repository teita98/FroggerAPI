package org.vedruna.frogger.controller.rest;


import jakarta.xml.bind.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.vedruna.frogger.dto.RecordScoreDTO;
import org.vedruna.frogger.persistance.model.User;
import org.vedruna.frogger.service.RecordScoreServiceI;


@RestController
@RequestMapping("/api/v1/score")
@CrossOrigin
public class ScoreController {
    @Autowired
    private RecordScoreServiceI recordScoreService;

    /**
     * GET - Devuelve la lista paginada de todas las puntuaciones.
     */
    @GetMapping
    public ResponseEntity<Page<RecordScoreDTO>> getAllScores(Pageable pageable) {
        return ResponseEntity.ok(recordScoreService.getAllScores(pageable));
    }

    /**
     * GET /me - Devuelve la puntuación del usuario logueado.
     */
    @GetMapping("/me")
    public ResponseEntity<RecordScoreDTO> getUserScore(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(recordScoreService.getUserScore(user.getUserId()));
    }

    /**
     * POST - Inserta la puntuación de un usuario.
     */
    @PostMapping
    public ResponseEntity<String> addScore(@RequestBody RecordScoreDTO recordScoreDTO) {
        recordScoreService.addScore(recordScoreDTO);
        return ResponseEntity.ok("Puntuación registrada correctamente");
    }

    /**
     * PUT - Actualiza la puntuación de un usuario. Si el nuevo tiempo es mayor (peor) que el registrado, se lanza una excepción de validación.
     */
    @PutMapping
    public ResponseEntity<RecordScoreDTO> updateScore(@RequestBody RecordScoreDTO recordScoreDTO) throws ValidationException {
        return ResponseEntity.ok(recordScoreService.updateScore(recordScoreDTO));
    }

}
