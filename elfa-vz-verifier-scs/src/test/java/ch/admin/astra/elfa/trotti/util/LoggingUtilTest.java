package ch.admin.astra.elfa.trotti.util;

import org.junit.jupiter.api.Test;
import org.slf4j.MDC;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;


class LoggingUtilTest {

    @Test
    void test_operation() {
        // when
        LoggingUtil.operation("operation");

        // then
        assertEquals("operation", MDC.get(LoggingUtil.KEY_OPERATION));
    }

    @Test
    void test_useCaseId() {
        // when
        LoggingUtil.useCaseId("useCaseId");

        // then
        assertEquals("useCaseId", MDC.get(LoggingUtil.KEY_USE_CASE_ID));
    }

    @Test
    void test_verificationId() {
        // when
        UUID verificationId = UUID.randomUUID();
        LoggingUtil.verificationId(verificationId);

        // then
        assertEquals(verificationId.toString(), MDC.get(LoggingUtil.KEY_VERIFICATION_ID));
    }
}