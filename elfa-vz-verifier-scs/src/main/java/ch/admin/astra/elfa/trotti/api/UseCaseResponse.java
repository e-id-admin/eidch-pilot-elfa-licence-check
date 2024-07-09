package ch.admin.astra.elfa.trotti.api;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;
import lombok.extern.jackson.Jacksonized;

import java.util.List;
import java.util.UUID;

@Data
@Jacksonized
@Builder(toBuilder = true)
public class UseCaseResponse {

    private UUID id;

    private String title;

    private String description;

    private Long order;

    @Singular
    private List<AttributeGroupResponse> attributeGroups;
}
