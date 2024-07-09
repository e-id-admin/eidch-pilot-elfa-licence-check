package ch.admin.astra.elfa.trotti.mapper;

import ch.admin.astra.elfa.trotti.api.VerificationBeginResponse;
import ch.admin.astra.elfa.trotti.api.VerificationStatusResponse;
import ch.admin.astra.elfa.trotti.domain.techadapter.client.model.VerificationResponse;
import ch.admin.astra.elfa.trotti.domain.verifier.model.VerificationStartData;
import org.mapstruct.Mapper;

@Mapper(uses = StatusMapper.class)
public interface VerificationMapper {

    VerificationBeginResponse map(VerificationStartData verificationStartData);

    VerificationStatusResponse map(VerificationResponse verificationResponse);
}