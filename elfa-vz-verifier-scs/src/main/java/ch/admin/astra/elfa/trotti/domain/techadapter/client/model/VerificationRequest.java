package ch.admin.astra.elfa.trotti.domain.techadapter.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Data
@Builder
@Jacksonized
public class VerificationRequest {

    @JsonProperty("required_attributes")
    private List<String> requiredAttributes;

    @JsonProperty("client_metadata")
    private VerifierMetaData metaData;

}
