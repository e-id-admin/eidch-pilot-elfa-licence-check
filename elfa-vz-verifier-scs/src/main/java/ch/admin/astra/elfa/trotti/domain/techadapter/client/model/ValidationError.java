package ch.admin.astra.elfa.trotti.domain.techadapter.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class ValidationError {

    @JsonProperty("detail")
    private ValidationErrorDetail[] validationErrorDetails;

}
