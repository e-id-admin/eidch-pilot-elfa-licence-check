package ch.admin.astra.elfa.trotti.domain.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum BusinessReason {

    UNEXPECTED_TECHNICAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "i18n.exception.technical"),
    TECH_ADAPTER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "i18n.exception.techAdapter"),;

    private final HttpStatus httpStatus;
    private final String error;

}
