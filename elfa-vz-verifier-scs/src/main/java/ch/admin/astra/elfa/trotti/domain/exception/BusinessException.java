package ch.admin.astra.elfa.trotti.domain.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class BusinessException extends RuntimeException {

    private final BusinessReason reason;

    private final List<String> details;

    public BusinessException(BusinessReason reason, List<String> details) {
        this.reason = reason;
        this.details = details;
    }
}
