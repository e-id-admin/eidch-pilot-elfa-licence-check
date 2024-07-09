package ch.admin.astra.elfa.trotti.api;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Data
@Jacksonized
@Builder(toBuilder = true)
public class AttributeGroupResponse {

        private String name;

        private Long order;

        @Singular
        private List<AttributeResponse> attributes;
}
