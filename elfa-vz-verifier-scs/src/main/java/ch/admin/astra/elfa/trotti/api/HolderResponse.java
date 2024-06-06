package ch.admin.astra.elfa.trotti.api;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;
import lombok.extern.jackson.Jacksonized;

import java.util.Map;

@Data
@Jacksonized
@Builder(toBuilder = true)
public class HolderResponse {

    @Singular
    private Map<String, String> attributes;

}
