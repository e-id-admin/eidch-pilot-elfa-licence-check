package ch.admin.astra.elfa.trotti.domain.verifier.exception;

import lombok.Getter;

@Getter
public class FileStorageException extends RuntimeException {

    public static final String EXCEPTION_MSG = "i18n.exception.file-storage";

    private final Throwable exception;


    public FileStorageException(Throwable exception) {
        this.exception = exception;
    }
}
