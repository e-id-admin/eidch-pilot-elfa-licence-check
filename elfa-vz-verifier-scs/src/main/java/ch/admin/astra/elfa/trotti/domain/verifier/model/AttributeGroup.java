package ch.admin.astra.elfa.trotti.domain.verifier.model;


import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Getter
@Builder
@Jacksonized
public final class AttributeGroup {

    private final String name;
    private final Long order;
    @Singular
    private final List<Attribute> attributes;
}
