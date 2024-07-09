package ch.admin.astra.elfa.trotti.controller.advice;

import ch.admin.astra.elfa.trotti.api.ErrorResponse;
import ch.admin.astra.elfa.trotti.domain.exception.BusinessException;
import ch.admin.astra.elfa.trotti.domain.techadapter.client.exception.TechAdapterException;
import ch.admin.astra.elfa.trotti.domain.verifier.exception.FileMappingException;
import ch.admin.astra.elfa.trotti.domain.verifier.exception.FileStorageException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static ch.admin.astra.elfa.trotti.domain.exception.BusinessReason.UNEXPECTED_TECHNICAL_ERROR;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@ControllerAdvice
public class GlobalExceptionControllerAdvice {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex) {
        log.error("An error occurred. Reason: {}, Details: {}", ex.getReason(), ex.getDetails(), ex);

        return ResponseEntity
                .status(ex.getReason().getHttpStatus())
                .body(ErrorResponse.builder()
                        .error(ex.getReason().getError())
                        .detail(ex.getDetails()).build());
    }

    @ExceptionHandler(FileMappingException.class)
    public ResponseEntity<ErrorResponse> handleFileMappingException(FileMappingException ex) {
        log.error("An error occurred. Could not map use case file: {} Details: {}", ex.getFileName(), ex.getException().getMessage());

        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.builder()
                        .error(FileMappingException.EXCEPTION_MSG)
                        .build());
    }

    @ExceptionHandler(FileStorageException.class)
    public ResponseEntity<ErrorResponse> handleFileStorageException(FileStorageException ex) {
        log.error("An error occurred. Error while reading use-case directory: {}", ex.getException().getMessage());

        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.builder()
                        .error(FileStorageException.EXCEPTION_MSG)
                        .build());
    }

    @ExceptionHandler(TechAdapterException.class)
    public ResponseEntity<ErrorResponse> handleTechAdapterException(TechAdapterException ex) {
        String message = ex.getException() != null ? ex.getException().getMessage() : String.join(" - ", ex.getDetails());
        log.error("An error occurred. Error while accessing the tech-adapter: {}", message);

        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.builder()
                        .error(TechAdapterException.EXCEPTION_MSG)
                        .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("An error occurred. Unexpected exception: {}", e.getMessage());
        log.error(e.toString());

        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.builder()
                        .error(UNEXPECTED_TECHNICAL_ERROR.getError())
                        .build());
    }
}
