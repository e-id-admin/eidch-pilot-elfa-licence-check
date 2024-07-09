package ch.admin.astra.elfa.trotti.domain.techadapter.client;

import ch.admin.astra.elfa.trotti.domain.techadapter.client.exception.TechAdapterException;
import ch.admin.astra.elfa.trotti.domain.techadapter.client.model.VerificationRequest;
import ch.admin.astra.elfa.trotti.domain.techadapter.client.model.VerificationResponse;

import java.util.UUID;

/**
 * Interface client to connect to the Verifier Tech-Adapter.
 */
public interface TechAdapterClient {

    /**
     * Start a verification process.
     *
     * @param verificationRequest request including the requested attributes
     * @return {@link VerificationResponse} with details about the verification process
     * @throws TechAdapterException when there are connection issues or invalid inputs
     */
    VerificationResponse startVerification(VerificationRequest verificationRequest);

    /**
     * Get current status about the verification process
     *
     * @param verificationId identifying id for the verification process
     * @return {@link VerificationResponse} with details about the verification process
     * @throws TechAdapterException when there are connection issues or invalid inputs
     */
    VerificationResponse getVerificationStatus(UUID verificationId);

}
