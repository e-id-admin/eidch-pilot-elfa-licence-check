package ch.admin.astra.elfa.trotti.util;

import ch.admin.astra.elfa.trotti.domain.techadapter.client.model.VerificationStatus;
import lombok.experimental.UtilityClass;
import org.slf4j.MDC;

import java.util.UUID;

@UtilityClass
public class LoggingUtil {

    public static final String OPERATION_FINISHED = "Operation finished";
    public static final String KEY_OPERATION = "operation";
    public static final String KEY_VERIFICATION_ID = "verificationId";
    public static final String KEY_ERROR_CODE = "errorCode";
    public static final String KEY_USE_CASE_ID = "useCaseId";
    public static final String KEY_VERIFICATION_STATUS = "verificationStatus";

    public void operation(String operation) {
        MDC.put(KEY_OPERATION, operation);
    }

    public void useCaseId(String useCaseId) {
        MDC.put(KEY_USE_CASE_ID, useCaseId);
    }

    public void verificationId(UUID verificationId) {
        MDC.put(KEY_VERIFICATION_ID, verificationId.toString());
    }

    public void errorCode(String errorCode) {
        MDC.put(KEY_ERROR_CODE, errorCode);
    }

    public void verificationStatus(VerificationStatus verificationStatus) {
        MDC.put(KEY_VERIFICATION_STATUS, verificationStatus.name());
    }
}
