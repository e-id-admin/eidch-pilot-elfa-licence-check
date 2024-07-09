package ch.admin.astra.elfa.trotti.domain.verifier.exception;

import lombok.Getter;

import java.util.UUID;

@Getter
public class UseCaseNotFoundException extends RuntimeException {

    public static final String EXCEPTION_MSG = "i18n.exception.use-case.not-found";

    private final UUID useCaseId;


    public UseCaseNotFoundException(UUID useCaseId) {
        this.useCaseId = useCaseId;
    }
}
