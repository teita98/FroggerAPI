package org.vedruna.frogger.dto;

import lombok.*;

@Getter
@Setter
public class RecordScoreDTO {
    private Integer recordId;
    private String recordTime;
    private Integer userId;

    public RecordScoreDTO() { }

    public RecordScoreDTO(Integer recordId, String recordTime, Integer userId) {
        this.recordId = recordId;
        this.recordTime = recordTime;
        this.userId = userId;
    }
    // Getters y Setters
    public Integer getRecordId() {
        return recordId;
    }
    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }
    public String getRecordTime() {
        return recordTime;
    }
    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
