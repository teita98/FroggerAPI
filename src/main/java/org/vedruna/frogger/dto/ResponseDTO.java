package org.vedruna.frogger.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
public class ResponseDTO {
    @NotNull
    private String message;
    public ResponseDTO() { }

    public ResponseDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
