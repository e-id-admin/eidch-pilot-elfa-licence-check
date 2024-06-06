package ch.admin.astra.elfa.trotti.api;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Jacksonized
@Builder(toBuilder = true)
public class VerificationStatusResponse {

    private String id;

    private VerificationStatus status;

    private String verificationUrl;

    private String errorCode;

    private String errorDescription;

    private HolderResponse holderResponse;

}
