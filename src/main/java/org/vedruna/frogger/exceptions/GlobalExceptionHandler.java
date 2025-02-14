package org.vedruna.frogger.exceptions;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import javax.naming.AuthenticationException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja excepciones de tipo RuntimeException.
     * Generalmente, se usa para capturar errores inesperados en la ejecución.
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Ocurrió un error inesperado");
        errorResponse.put("detalle", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    /**
     * Maneja excepciones de integridad de datos, como claves duplicadas en la base de datos.
     * Se usa cuando un usuario intenta registrar un email o nombre de usuario ya existente.
     */
    @ExceptionHandler({SQLIntegrityConstraintViolationException.class, DataIntegrityViolationException.class})
    public ResponseEntity<Map<String, String>> handleDuplicateEntryException(Exception ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Datos duplicados");
        errorResponse.put("detalle", "El usuario o email ya están registrados");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * Maneja excepciones de validación cuando los datos enviados en la solicitud no cumplen con los requisitos.
     * Por ejemplo, si un campo obligatorio no está presente o no cumple con las reglas definidas en la entidad.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errorResponse.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * Maneja excepciones cuando un usuario intenta acceder a un recurso sin los permisos adecuados.
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, String>> handleAccessDeniedException(AccessDeniedException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Acceso denegado");
        errorResponse.put("detalle", "No tienes permisos para realizar esta acción");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

    /**
     * Maneja excepciones de autenticación cuando un usuario no ha iniciado sesión o el token es inválido.
     */
    @ExceptionHandler({AuthenticationException.class, ExpiredJwtException.class, SignatureException.class})
    public ResponseEntity<Map<String, String>> handleAuthenticationException(Exception ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Error de autenticación");
        errorResponse.put("detalle", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }


    /**
     * Maneja excepciones cuando un recurso no es encontrado en la base de datos.
     * Por ejemplo, si se intenta obtener un usuario con un ID inexistente.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFoundException(EntityNotFoundException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Usuario no encontrado");
        errorResponse.put("detalle", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    /**
     * Maneja excepciones cuando falta un parámetro obligatorio en la solicitud.
     */
    @ExceptionHandler({IllegalArgumentException.class, MissingServletRequestParameterException.class})
    public ResponseEntity<Map<String, String>> handleMissingDataException(Exception ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Solicitud incorrecta");
        errorResponse.put("detalle", "Falta algún dato obligatorio en la solicitud. Verifica e inténtalo nuevamente.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * Maneja excepciones cuando el formato de los datos enviados en la solicitud no es válido.
     * Por ejemplo, si se espera un número y se envía una cadena de texto.
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleInvalidFormatException(HttpMessageNotReadableException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Formato de datos inválido");
        errorResponse.put("detalle", "El formato de los datos enviados no es válido. Verifica los valores y prueba de nuevo.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * Maneja excepciones cuando se intenta realizar una operación no permitida.
     * Se usa, por ejemplo, cuando se intenta eliminar un usuario con datos relacionados.
     */
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, String>> handleIllegalStateException(IllegalStateException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Operación no permitida");
        errorResponse.put("detalle", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * Maneja cualquier otra excepción no controlada explícitamente.
     * Se usa como último recurso para evitar que la aplicación falle sin control.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGlobalException(Exception ex, WebRequest request) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Error interno del servidor");
        errorResponse.put("detalle", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(jakarta.validation.ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleConstraintViolationException(jakarta.validation.ConstraintViolationException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Error de validación");

        StringBuilder detalles = new StringBuilder();
        ex.getConstraintViolations().forEach(violation ->
                detalles.append(violation.getPropertyPath()).append(": ").append(violation.getMessage()).append("; ")
        );

        errorResponse.put("detalle", detalles.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(ValidationException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Error de validación");
        errorResponse.put("detalle", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

}
