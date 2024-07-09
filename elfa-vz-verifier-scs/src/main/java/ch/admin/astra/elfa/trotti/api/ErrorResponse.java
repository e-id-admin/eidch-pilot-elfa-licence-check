package ch.admin.astra.elfa.trotti.api;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Data
@Jacksonized
@Builder(toBuilder = true)
public final class ErrorResponse {

    private final String error;

    private final List<String> detail;
}
