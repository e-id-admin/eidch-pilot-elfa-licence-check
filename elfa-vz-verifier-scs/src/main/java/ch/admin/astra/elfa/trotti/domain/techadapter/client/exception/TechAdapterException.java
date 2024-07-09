package ch.admin.astra.elfa.trotti.domain.techadapter.client.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class TechAdapterException extends RuntimeException {

    public static final String EXCEPTION_MSG = "i18n.exception.tech-adapter";

    private final Throwable exception;

    private final List<String> details;

    public TechAdapterException(Throwable exception, List<String> details) {
        this.exception = exception;
        this.details = details;
    }
}
