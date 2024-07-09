package ch.admin.astra.elfa.trotti.mapper;

import ch.admin.astra.elfa.trotti.api.UseCaseResponse;
import ch.admin.astra.elfa.trotti.domain.verifier.model.UseCase;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface UseCaseMapper {

    UseCaseResponse map(UseCase useCase);

    List<UseCaseResponse> map(List<UseCase> credentials);
}
