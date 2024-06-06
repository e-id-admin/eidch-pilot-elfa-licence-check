package ch.admin.astra.elfa.trotti.domain.verifier.model;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UseCaseTest {

    @Test
    void getSortedAttributes_noAttributeGroups() {
        UseCase useCase = UseCase.builder().build();

        assertTrue(useCase.getSortedAttributes().isEmpty());
    }

    @Test
    void getSortedAttributes_attributeGroupsEmpty() {
        UseCase useCase = UseCase.builder()
                .attributeGroups(Collections.emptyList())
                .build();

        assertTrue(useCase.getSortedAttributes().isEmpty());
    }

    @Test
    void getSortedAttributes_oneAttributeGroup_oneAttribute() {
        UseCase useCase = UseCase.builder()
                .attributeGroup(AttributeGroup.builder()
                        .attribute(Attribute.builder().order(1L).build())
                        .build())
                .build();

        assertEquals(1, useCase.getSortedAttributes().size());
    }

    @Test
    void getSortedAttributes_oneAttributeGroup_multipleAttributes() {
        UseCase useCase = UseCase.builder()
                .attributeGroup(AttributeGroup.builder()
                        .attribute(Attribute.builder().name("second").order(10L).build())
                        .attribute(Attribute.builder().name("first").order(2L).build())
                        .attribute(Attribute.builder().name("third").order(20L).build())
                        .build())
                .build();

        List<String> sortedAttributes = useCase.getSortedAttributes();
        assertEquals(3, sortedAttributes.size());
        assertEquals("first", sortedAttributes.get(0));
        assertEquals("second", sortedAttributes.get(1));
        assertEquals("third", sortedAttributes.get(2));
    }

    @Test
    void getSortedAttributes_multipleAttributeGroup() {
        UseCase useCase = UseCase.builder()
                .attributeGroup(AttributeGroup.builder()
                        .attribute(Attribute.builder().name("second").order(10L).build())
                        .attribute(Attribute.builder().name("fourth").order(100L).build())
                        .build())
                .attributeGroup(AttributeGroup.builder()
                        .attribute(Attribute.builder().name("third").order(20L).build())
                        .build())
                .attributeGroup(AttributeGroup.builder()
                        .attribute(Attribute.builder().name("first").order(2L).build())
                        .build())
                .build();

        List<String> sortedAttributes = useCase.getSortedAttributes();
        assertEquals(4, sortedAttributes.size());
        assertEquals("first", sortedAttributes.get(0));
        assertEquals("second", sortedAttributes.get(1));
        assertEquals("third", sortedAttributes.get(2));
        assertEquals("fourth", sortedAttributes.get(3));
    }
}