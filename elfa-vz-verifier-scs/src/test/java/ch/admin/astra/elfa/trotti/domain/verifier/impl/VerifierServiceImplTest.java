package ch.admin.astra.elfa.trotti.domain.verifier.impl;

import ch.admin.astra.elfa.trotti.domain.exception.BusinessException;
import ch.admin.astra.elfa.trotti.domain.qrcode.QrCodeService;
import ch.admin.astra.elfa.trotti.domain.qrcode.model.QrCode;
import ch.admin.astra.elfa.trotti.domain.techadapter.client.TechAdapterClient;
import ch.admin.astra.elfa.trotti.domain.techadapter.client.exception.TechAdapterException;
import ch.admin.astra.elfa.trotti.domain.techadapter.client.model.VerificationRequest;
import ch.admin.astra.elfa.trotti.domain.techadapter.client.model.VerificationResponse;
import ch.admin.astra.elfa.trotti.domain.techadapter.client.model.VerificationStatus;
import ch.admin.astra.elfa.trotti.domain.verifier.UseCaseCache;
import ch.admin.astra.elfa.trotti.domain.verifier.exception.UseCaseNotFoundException;
import ch.admin.astra.elfa.trotti.domain.verifier.model.Attribute;
import ch.admin.astra.elfa.trotti.domain.verifier.model.AttributeGroup;
import ch.admin.astra.elfa.trotti.domain.verifier.model.UseCase;
import ch.admin.astra.elfa.trotti.domain.verifier.model.VerificationStartData;
import ch.admin.astra.elfa.trotti.util.LoggingUtil;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

import static ch.admin.astra.elfa.trotti.util.LoggingUtil.OPERATION_FINISHED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VerifierServiceImplTest {


    public static final String CLIENT_NAME = "Client";
    public static final String LOGO_URI = "URI";
    private VerifierServiceImpl verifierService;

    @Mock
    private UseCaseCache useCaseCache;

    @Mock
    private TechAdapterClient techAdapterClient;

    @Mock
    private QrCodeService qrCodeService;

    private ListAppender<ILoggingEvent> logWatcher;

    @BeforeEach
    void setUp() {
        verifierService = new VerifierServiceImpl(useCaseCache, techAdapterClient, qrCodeService, CLIENT_NAME, LOGO_URI);

        logWatcher = new ListAppender<>();
        logWatcher.start();
        ((Logger) LoggerFactory.getLogger(VerifierServiceImpl.class)).addAppender(logWatcher);
    }

    @AfterEach
    void teardown() {
        ((Logger) LoggerFactory.getLogger(VerifierServiceImpl.class)).detachAndStopAllAppenders();
    }

    @Test
    void testGetUseCases_callsCache() {
        verifierService.getUseCases();
        Mockito.verify(useCaseCache).getUseCases();
    }

    @Test
    void startVerification_UseCaseNotFoundException() {
        // given
        UUID useCaseId = UUID.randomUUID();
        when(useCaseCache.getUseCaseById(useCaseId)).thenThrow(UseCaseNotFoundException.class);

        // when
        Assertions.assertThrows(UseCaseNotFoundException.class, () -> verifierService.startVerification(useCaseId));

        // then
        Mockito.verify(useCaseCache).getUseCaseById(useCaseId);
    }

    @Test
    void startVerification_TechAdapterException() {
        // given
        // mock useCaseCache
        UUID useCaseId = UUID.randomUUID();
        when(useCaseCache.getUseCaseById(useCaseId)).thenReturn(mockUseCase());

        // mock techAdapterClient
        when(techAdapterClient.startVerification(any())).thenThrow(TechAdapterException.class);

        // when
        Assertions.assertThrows(TechAdapterException.class, () -> verifierService.startVerification(useCaseId));

        // then
        // assert useCaseService
        Mockito.verify(useCaseCache).getUseCaseById(useCaseId);

        // assert VerificationRequest
        ArgumentCaptor<VerificationRequest> verificationRequestArgumentCaptor = ArgumentCaptor.forClass(VerificationRequest.class);
        Mockito.verify(techAdapterClient).startVerification(verificationRequestArgumentCaptor.capture());
        VerificationRequest verificationRequest = verificationRequestArgumentCaptor.getValue();
        assertIterableEquals(List.of("attribute2", "attribute"), verificationRequest.getRequiredAttributes());
        assertEquals(CLIENT_NAME, verificationRequest.getMetaData().getClientName());
        assertEquals(LOGO_URI, verificationRequest.getMetaData().getLogoUri());
    }

    @Test
    void startVerification_BusinessException() {
        // given
        // mock useCaseCache
        UUID useCaseId = UUID.randomUUID();
        when(useCaseCache.getUseCaseById(useCaseId)).thenReturn(mockUseCase());

        // mock techAdapterClient
        UUID verificationId = UUID.randomUUID();
        String verificationUrlExpected = "URL";
        VerificationResponse verificationResponse = VerificationResponse.builder()
                .id(verificationId)
                .status(VerificationStatus.SUCCESS)
                .verificationUrl(verificationUrlExpected)
                .build();
        when(techAdapterClient.startVerification(any())).thenReturn(verificationResponse);

        // mock qrCodeService
        when(qrCodeService.create(verificationUrlExpected, 500)).thenThrow(BusinessException.class);

        // when
        assertThrows(BusinessException.class, () -> verifierService.startVerification(useCaseId));

        // then
        // assert useCaseService
        Mockito.verify(useCaseCache).getUseCaseById(useCaseId);
        Mockito.verify(techAdapterClient).startVerification(Mockito.any());
        Mockito.verify(qrCodeService).create(verificationUrlExpected, 500);
    }


    @Test
    void startVerification_OK() {
        // given
        // mock useCaseCache
        UUID useCaseId = UUID.randomUUID();
        when(useCaseCache.getUseCaseById(useCaseId)).thenReturn(mockUseCase());

        // mock techAdapterClient
        UUID verificationId = UUID.randomUUID();
        String verificationUrlExpected = "URL";
        VerificationResponse verificationResponse = VerificationResponse.builder()
                .id(verificationId)
                .status(VerificationStatus.SUCCESS)
                .verificationUrl(verificationUrlExpected)
                .build();

        when(techAdapterClient.startVerification(any())).thenReturn(verificationResponse);

        // mock qrCodeService
        byte[] imageBytes = "image".getBytes();
        String imageFormat = "format";
        when(qrCodeService.create(verificationUrlExpected, 500)).thenReturn(QrCode.builder().imageData(imageBytes).format(imageFormat).build());

        // when
        VerificationStartData result = verifierService.startVerification(useCaseId);

        // then
        // assert result
        assertEquals(verificationId, result.getId());
        assertEquals(imageBytes, result.getQrCode());
        assertEquals(imageFormat, result.getQrCodeFormat());

        // assert useCaseService
        Mockito.verify(useCaseCache).getUseCaseById(useCaseId);

        // assert VerificationRequest
        ArgumentCaptor<VerificationRequest> verificationRequestArgumentCaptor = ArgumentCaptor.forClass(VerificationRequest.class);
        Mockito.verify(techAdapterClient).startVerification(verificationRequestArgumentCaptor.capture());
        VerificationRequest verificationRequest = verificationRequestArgumentCaptor.getValue();
        assertIterableEquals(List.of("attribute2", "attribute"), verificationRequest.getRequiredAttributes());
        assertEquals(CLIENT_NAME, verificationRequest.getMetaData().getClientName());
        assertEquals(LOGO_URI, verificationRequest.getMetaData().getLogoUri());

        // assert QrCode
        ArgumentCaptor<String> qrCodeArgumentCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(qrCodeService).create(qrCodeArgumentCaptor.capture(), eq(500));
        String verificationUrlActual = qrCodeArgumentCaptor.getValue();
        assertEquals(verificationUrlExpected, verificationUrlActual);
    }

    @Test
    void getVerificationStatus_throwsTechAdapterException() {
        when(techAdapterClient.getVerificationStatus(any())).thenThrow(TechAdapterException.class);

        UUID verificationId = UUID.randomUUID();
        assertThrows(TechAdapterException.class, () -> verifierService.getVerificationStatus(verificationId));
    }

    @Test
    void getVerificationStatus_isSuccess() {
        // mock techAdapterClient
        when(techAdapterClient.getVerificationStatus(any())).thenReturn(VerificationResponse.builder()
                .status(VerificationStatus.SUCCESS)
                .build());

        try (MockedStatic<LoggingUtil> structuredArgumentFactoryMockedStatic = Mockito.mockStatic(LoggingUtil.class)) {
            verifierService.getVerificationStatus(UUID.randomUUID());

            structuredArgumentFactoryMockedStatic.verify(() -> LoggingUtil.verificationStatus(VerificationStatus.SUCCESS));
            structuredArgumentFactoryMockedStatic.verifyNoMoreInteractions();
        }

        ILoggingEvent logMessage = logWatcher.list.get(0);
        assertThat(logMessage.getLevel()).isEqualTo(Level.INFO);
        assertThat(logMessage.getFormattedMessage()).contains(OPERATION_FINISHED);
    }

    @Test
    void getVerificationStatus_isFunctionallyFailed() {
        // mock techAdapterClient
        String errorCode = "credential_expired";
        VerificationStatus verificationStatus = VerificationStatus.FAILED;
        when(techAdapterClient.getVerificationStatus(any())).thenReturn(VerificationResponse.builder()
                .status(verificationStatus)
                .errorCode(errorCode)
                .build());

        try (MockedStatic<LoggingUtil> structuredArgumentFactoryMockedStatic = Mockito.mockStatic(LoggingUtil.class)) {
            verifierService.getVerificationStatus(UUID.randomUUID());

            structuredArgumentFactoryMockedStatic.verify(() -> LoggingUtil.verificationStatus(verificationStatus));
            structuredArgumentFactoryMockedStatic.verify(() -> LoggingUtil.errorCode(errorCode));
            structuredArgumentFactoryMockedStatic.verifyNoMoreInteractions();
        }

        ILoggingEvent logMessage = logWatcher.list.get(0);
        assertThat(logMessage.getLevel()).isEqualTo(Level.INFO);
        assertThat(logMessage.getFormattedMessage()).contains(OPERATION_FINISHED);
    }

    @Test
    void getVerificationStatus_isTechnicallyFailed() {
        // mock techAdapterClient
        String errorCode = "notFunctional";
        VerificationStatus verificationStatus = VerificationStatus.FAILED;
        when(techAdapterClient.getVerificationStatus(any())).thenReturn(VerificationResponse.builder()
                .status(verificationStatus)
                .errorCode(errorCode)
                .build());

        try (MockedStatic<LoggingUtil> structuredArgumentFactoryMockedStatic = Mockito.mockStatic(LoggingUtil.class)) {
            verifierService.getVerificationStatus(UUID.randomUUID());

            structuredArgumentFactoryMockedStatic.verify(() -> LoggingUtil.verificationStatus(verificationStatus));
            structuredArgumentFactoryMockedStatic.verify(() -> LoggingUtil.errorCode(errorCode));
            structuredArgumentFactoryMockedStatic.verifyNoMoreInteractions();
        }

        ILoggingEvent logMessage = logWatcher.list.get(0);
        assertThat(logMessage.getLevel()).isEqualTo(Level.ERROR);
        assertThat(logMessage.getFormattedMessage()).contains(OPERATION_FINISHED);
    }

    private UseCase mockUseCase() {
        return UseCase.builder()
                .attributeGroup(AttributeGroup.builder()
                        .attribute(Attribute.builder().name("attribute").order(2L).build())
                        .attribute(Attribute.builder().name("attribute2").order(1L).build())
                        .build())
                .build();
    }
}