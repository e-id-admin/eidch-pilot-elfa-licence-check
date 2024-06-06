package ch.admin.astra.elfa.trotti.domain.techadapter.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

@Data
@Builder
@Jacksonized
public class VerificationResponse {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("status")
    private VerificationStatus status;

    @JsonProperty("verification_url")
    private String verificationUrl;

    @JsonProperty("error_code")
    private String errorCode;

    @JsonProperty("error_description")
    private String errorDescription;

    @JsonProperty("holder_response")
    private HolderResponse holderResponse;

}
