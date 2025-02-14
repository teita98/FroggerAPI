package org.vedruna.frogger.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.xml.bind.ValidationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.vedruna.frogger.dto.RecordScoreDTO;
import org.vedruna.frogger.persistance.model.RecordScore;
import org.vedruna.frogger.persistance.model.User;
import org.vedruna.frogger.persistance.repository.RecordScoreRepositoryI;
import org.vedruna.frogger.persistance.repository.UserRepositoryI;
;
import java.time.LocalTime;


@Service
public class RecordScoreServiceImpl implements RecordScoreServiceI {

    @Autowired
    private RecordScoreRepositoryI recordScoreRepository;
    @Autowired
    private UserRepositoryI userRepository;


    // Usado para GET /score - ScoreControll
    @Override
    public Page<RecordScoreDTO> getAllScores(Pageable pageable) {
        return recordScoreRepository.findAll(pageable)
                .map(score -> new RecordScoreDTO(score.getRecordId(), score.getRecordTime().toString(), score.getUser().getUserId()));
    }

    // Usado para GET /me - ScoreControll
    @Override
    public RecordScoreDTO getUserScore(Integer userId) {
        RecordScore score = recordScoreRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("No se encontró puntuación para el usuario."));
        return new RecordScoreDTO(score.getRecordId(), score.getRecordTime().toString(), score.getUser().getUserId());
    }

    // Usado para POST /score - ScoreControll
    @Override
    @Transactional
    public void addScore(RecordScoreDTO dto) {
        User user = userRepository.findByUserId(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
        // Verificar si el usuario ya tiene un record
        if (recordScoreRepository.findByUser(user).isPresent()) {
            throw new IllegalStateException("El usuario ya tiene una puntuación registrada. Debe actualizarla.");
        }

        RecordScore record = new RecordScore();
        record.setUser(user);
        record.setRecordTime(LocalTime.parse(dto.getRecordTime()));
        recordScoreRepository.save(record);
    }

    // Usada para PUT /score - ScoreController
    @Override
    @Transactional
    public RecordScoreDTO updateScore(RecordScoreDTO dto) throws ValidationException {
        RecordScore score = recordScoreRepository.findByUserId(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("No se encontró puntuación para el usuario."));
        LocalTime newTime = LocalTime.parse(dto.getRecordTime());
        // Validar si el nuevo tiempo es mayor (peor)
        if (newTime.isAfter(score.getRecordTime())) {
            throw new ValidationException("No se puede actualizar, el nuevo tiempo es mayor.");
        }
        score.setRecordTime(newTime);
        recordScoreRepository.save(score);
        return new RecordScoreDTO(score.getRecordId(), score.getRecordTime().toString(), score.getUser().getUserId());
    }
}