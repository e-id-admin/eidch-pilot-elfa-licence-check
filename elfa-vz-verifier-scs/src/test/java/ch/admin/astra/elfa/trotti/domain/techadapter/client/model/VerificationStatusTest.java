package ch.admin.astra.elfa.trotti.domain.techadapter.client.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class VerificationStatusTest {

    @Test
    void isSuccess_true() {
        assertTrue(VerificationStatus.isSuccess(VerificationStatus.SUCCESS));
    }

    @ParameterizedTest
    @EnumSource(value = VerificationStatus.class, names = {"PENDING", "FAILED"})
    void isSuccess_false(VerificationStatus status) {
        assertFalse(VerificationStatus.isSuccess(status));
    }

    @ParameterizedTest
    @EnumSource(value = VerificationStatus.class, names = {"PENDING", "SUCCESS"})
    void hasFunctionallyFailed_wrongStatus_false(VerificationStatus status) {
        VerificationResponse verificationResponse = VerificationResponse.builder()
                .status(status)
                .build();

        assertFalse(VerificationStatus.hasFunctionallyFailed(verificationResponse));
    }

    @Test
    void hasFunctionallyFailed_wrongErrorCode_false() {
        VerificationResponse verificationResponse = VerificationResponse.builder()
                .status(VerificationStatus.FAILED)
                .errorCode("notFunctional")
                .build();

        assertFalse(VerificationStatus.hasFunctionallyFailed(verificationResponse));
    }

    @ParameterizedTest
    @MethodSource("ch.admin.astra.elfa.trotti.domain.techadapter.client.model.VerificationStatus#getFunctionalErrorCodes")
    void hasFunctionallyFailed_true(String errorCode) {
        VerificationResponse verificationResponse = VerificationResponse.builder()
                .status(VerificationStatus.FAILED)
                .errorCode(errorCode)
                .build();

        assertTrue(VerificationStatus.hasFunctionallyFailed(verificationResponse));
    }
}