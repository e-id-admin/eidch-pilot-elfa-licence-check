package ch.admin.astra.elfa.trotti.domain.verifier.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import lombok.extern.jackson.Jacksonized;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
@Jacksonized
public final class UseCase {

    private final UUID id;
    private final String title;
    private final String description;
    private final Long order;
    @Singular
    private final List<AttributeGroup> attributeGroups;

    public List<String> getSortedAttributes() {
        if (attributeGroups == null || attributeGroups.isEmpty()) {
            return Collections.emptyList();
        }

        return getAttributeGroups().stream()
                .flatMap(ag -> ag.getAttributes().stream())
                .sorted(Comparator.comparing(Attribute::getOrder))
                .map(Attribute::getName).toList();
    }

}
