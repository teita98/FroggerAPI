package org.vedruna.frogger.service;

import jakarta.xml.bind.ValidationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.vedruna.frogger.dto.RecordScoreDTO;
import java.util.List;

public interface RecordScoreServiceI {
    Page<RecordScoreDTO> getAllScores(Pageable pageable);
    RecordScoreDTO getUserScore(Integer userId);
    void addScore(RecordScoreDTO dto);
    RecordScoreDTO updateScore(RecordScoreDTO dto) throws ValidationException;
}
