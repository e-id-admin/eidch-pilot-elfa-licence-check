package ch.admin.astra.elfa.trotti.mapper;

import ch.admin.astra.elfa.trotti.api.UseCaseResponse;
import ch.admin.astra.elfa.trotti.domain.verifier.model.UseCase;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(uses = AttributeGroupMapper.class)
public interface UseCaseMapper {

    @Mapping(source = "attributeGroups", target = "attributeGroups")
    UseCaseResponse map(UseCase useCase);

    List<UseCaseResponse> map(List<UseCase> credentials);
}
