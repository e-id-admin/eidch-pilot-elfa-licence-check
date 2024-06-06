package ch.admin.astra.elfa.trotti.domain.techadapter.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class VerifierMetaData {

    @JsonProperty("client_name")
    private String clientName;

    @JsonProperty("logo_uri")
    private String logoUri;
}
