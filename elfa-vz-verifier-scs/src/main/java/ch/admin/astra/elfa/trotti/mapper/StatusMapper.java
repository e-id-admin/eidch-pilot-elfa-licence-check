package ch.admin.astra.elfa.trotti.mapper;

import ch.admin.astra.elfa.trotti.api.VerificationStatus;
import org.mapstruct.Mapper;

@Mapper
public interface StatusMapper {

    VerificationStatus map(ch.admin.astra.elfa.trotti.domain.techadapter.client.model.VerificationStatus verificationStatus);
}