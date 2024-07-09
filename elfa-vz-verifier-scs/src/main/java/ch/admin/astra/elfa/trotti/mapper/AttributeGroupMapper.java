package ch.admin.astra.elfa.trotti.mapper;

import ch.admin.astra.elfa.trotti.api.AttributeGroupResponse;
import ch.admin.astra.elfa.trotti.domain.verifier.model.AttributeGroup;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface AttributeGroupMapper {

    @Mapping(source = "attributes", target = "attributes")
    AttributeGroupResponse map(AttributeGroup useCase);

    List<AttributeGroupResponse> map(List<AttributeGroup> credentials);
}
