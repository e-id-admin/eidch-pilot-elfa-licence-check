package ch.admin.astra.elfa.trotti.domain.verifier.exception;

import lombok.Getter;

@Getter
public class FileMappingException extends RuntimeException {

    public static final String EXCEPTION_MSG = "i18n.exception.file-mapping";

    private final Throwable exception;

    private final String fileName;


    public FileMappingException(Throwable exception, String fileName) {
        this.exception = exception;
        this.fileName = fileName;
    }
}
