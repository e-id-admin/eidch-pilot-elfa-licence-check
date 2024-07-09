package ch.admin.astra.elfa.trotti.domain.techadapter.client.impl;

import ch.admin.astra.elfa.trotti.domain.exception.BusinessException;
import ch.admin.astra.elfa.trotti.domain.exception.BusinessReason;
import ch.admin.astra.elfa.trotti.domain.techadapter.client.TechAdapterClient;
import ch.admin.astra.elfa.trotti.domain.techadapter.client.model.HolderResponse;
import ch.admin.astra.elfa.trotti.domain.techadapter.client.model.VerificationRequest;
import ch.admin.astra.elfa.trotti.domain.techadapter.client.model.VerificationResponse;
import ch.admin.astra.elfa.trotti.domain.techadapter.client.model.VerificationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.Base64;
import java.util.Random;
import java.util.UUID;

/**
 * Mock Implementation class for the {@link TechAdapterClient} interface to test locally.
 */
@RequiredArgsConstructor
public class MockTechAdapterClientImpl implements TechAdapterClient {

    private final Random random = new Random();

    @Override
    public VerificationResponse startVerification(VerificationRequest verificationRequest) {
        return buildSuccessResponse(UUID.fromString("534a8d81-081f-4f01-9e37-38856c8b06e4"), VerificationStatus.PENDING);
    }

    @Override
    public VerificationResponse getVerificationStatus(UUID verificationId) {
        int i = random.nextInt(3);

        // uncomment to return an invalid response
        // return buildFailedResponse(verificationId, "credential_invalid", "Provided credential is not valid.");

        // returns a valid response
        return buildSuccessResponse(verificationId, i == 1 ? VerificationStatus.SUCCESS : VerificationStatus.PENDING);
    }

    private VerificationResponse buildSuccessResponse(UUID verificationId, VerificationStatus status) {
        return VerificationResponse.builder()
                .id(verificationId)
                .status(status)
                .verificationUrl("https://www.eid.admin.ch/")
                .errorCode(null)
                .errorDescription(null)
                .holderResponse(HolderResponse.builder()
                        .attribute("photoImage", getPhotoImageAsString())
                        .attribute("categoryCode", "B")
                        .attribute("categoryRestrictions", "10.01 Handschaltung")
                        .attribute("restrictionsA", "05.05 Fahren nur mit Beifahrer, der im Besitz eines Führerausweises sein muss")
                        .attribute("restrictionsB", "05.04 Fahren nur mit Beifahrer")
                        .attribute("firstName", "Seraina Manuela")
                        .attribute("lastName", "Muster")
                        .attribute("dateOfExpiration", "31.12.2025")
                        .attribute("registrationNumber", "001")
                        .attribute("faberPin", "123456789")
                        .attribute("credentialNumber", "123456789001")
                        .attribute("dateOfBirth", "01.01.2000")
                        .attribute("issuerEntity", "BE")
                        .attribute("issuerEntityDate", "01.01.2023")
                        .build())
                .build();
    }

    @SuppressWarnings("java:S1144")
    private VerificationResponse buildFailedResponse(UUID verificationId, String errorCode, String errorDescription) {
        return VerificationResponse.builder()
                .id(verificationId)
                .status(VerificationStatus.FAILED)
                .verificationUrl("https://www.eid.admin.ch/")
                .errorCode(errorCode)
                .errorDescription(errorDescription)
                .holderResponse(HolderResponse.builder().build())
                .build();
    }

    private String getPhotoImageAsString() {
        try {
            return Base64.getEncoder()
                    .encodeToString(new ClassPathResource("sample/sampleProfilePicture.png").getContentAsByteArray());
        } catch (IOException e) {
            throw new BusinessException(BusinessReason.UNEXPECTED_TECHNICAL_ERROR, null);
        }
    }
}
