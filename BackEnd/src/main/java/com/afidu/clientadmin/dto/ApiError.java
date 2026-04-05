package com.afidu.clientadmin.dto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * En la clase ApiError se representa la estructura estándar de respuesta para el manejo de errores de la API.
 *
 * @author hsgdevelop
 * @fechaCreacion 04/04/2026
 * @version 1.0.0
 */

public class ApiError {

    private LocalDateTime timestamp;
    private int status;
    private String error;
    private List<String> details;

    public ApiError(LocalDateTime timestamp, int status, String error, List<String> details) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.details = details;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public List<String> getDetails() {
        return details;
    }
}
