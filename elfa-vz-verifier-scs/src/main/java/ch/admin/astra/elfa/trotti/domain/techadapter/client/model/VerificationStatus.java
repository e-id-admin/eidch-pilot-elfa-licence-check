package ch.admin.astra.elfa.trotti.domain.techadapter.client.model;

import java.util.List;

public enum VerificationStatus {

    PENDING,
    SUCCESS,
    FAILED;

    public static boolean isSuccess(VerificationStatus status) {
        return SUCCESS.equals(status);
    }

    public static boolean isPending(VerificationStatus status) {
        return PENDING.equals(status);
    }

    public static boolean hasFunctionallyFailed(VerificationResponse response) {
        return FAILED.equals(response.getStatus()) &&
               getFunctionalErrorCodes().contains(response.getErrorCode());
    }

    private static List<String> getFunctionalErrorCodes() {
        return List.of(
                "client_rejected",
                "credential_expired",
                "jwt_expired",
                "credential_revoked",
                "credential_suspended"
        );
    }
}
