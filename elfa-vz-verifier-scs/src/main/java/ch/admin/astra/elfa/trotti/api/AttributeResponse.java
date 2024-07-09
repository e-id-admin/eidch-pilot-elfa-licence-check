package ch.admin.astra.elfa.trotti.api;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Jacksonized
@Builder(toBuilder = true)
public class AttributeResponse {

        private String name;

        private String type;

        private Long order;

}
