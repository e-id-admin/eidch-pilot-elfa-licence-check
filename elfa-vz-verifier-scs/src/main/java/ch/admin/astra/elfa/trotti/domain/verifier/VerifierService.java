package ch.admin.astra.elfa.trotti.domain.verifier;

import ch.admin.astra.elfa.trotti.domain.techadapter.client.model.VerificationResponse;
import ch.admin.astra.elfa.trotti.domain.verifier.model.UseCase;
import ch.admin.astra.elfa.trotti.domain.verifier.model.VerificationStartData;

import java.util.List;
import java.util.UUID;

public interface VerifierService {

    List<UseCase> getUseCases();

    VerificationStartData startVerification(UUID useCaseId);

    VerificationResponse getVerificationStatus(UUID verificationId);
}
