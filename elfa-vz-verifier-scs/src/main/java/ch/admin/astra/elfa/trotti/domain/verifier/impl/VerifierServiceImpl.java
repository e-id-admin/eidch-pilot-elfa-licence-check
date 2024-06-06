package ch.admin.astra.elfa.trotti.domain.verifier.impl;

import ch.admin.astra.elfa.trotti.domain.qrcode.QrCodeService;
import ch.admin.astra.elfa.trotti.domain.qrcode.model.QrCode;
import ch.admin.astra.elfa.trotti.domain.techadapter.client.TechAdapterClient;
import ch.admin.astra.elfa.trotti.domain.techadapter.client.model.VerificationRequest;
import ch.admin.astra.elfa.trotti.domain.techadapter.client.model.VerificationResponse;
import ch.admin.astra.elfa.trotti.domain.techadapter.client.model.VerificationStatus;
import ch.admin.astra.elfa.trotti.domain.techadapter.client.model.VerifierMetaData;
import ch.admin.astra.elfa.trotti.domain.verifier.UseCaseCache;
import ch.admin.astra.elfa.trotti.domain.verifier.VerifierService;
import ch.admin.astra.elfa.trotti.domain.verifier.model.UseCase;
import ch.admin.astra.elfa.trotti.domain.verifier.model.VerificationStartData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

import static ch.admin.astra.elfa.trotti.domain.techadapter.client.model.VerificationStatus.isPending;
import static ch.admin.astra.elfa.trotti.domain.techadapter.client.model.VerificationStatus.isSuccess;
import static ch.admin.astra.elfa.trotti.util.LoggingUtil.*;

@Slf4j
@Component
public class VerifierServiceImpl implements VerifierService {

    private final UseCaseCache useCaseCache;

    private final TechAdapterClient techAdapterClient;

    private final QrCodeService qrCodeService;

    private final String clientName;

    private final String logoUri;

    public VerifierServiceImpl(UseCaseCache useCaseCache,
                               TechAdapterClient techAdapterClient,
                               QrCodeService qrCodeService,
                               @Value("${verifier.metaData.clientName}") String clientName,
                               @Value("${verifier.metaData.logoUri}") String logoUri) {
        this.useCaseCache = useCaseCache;
        this.techAdapterClient = techAdapterClient;
        this.qrCodeService = qrCodeService;
        this.clientName = clientName;
        this.logoUri = logoUri;
    }

    @Override
    public List<UseCase> getUseCases() {
        List<UseCase> useCases = useCaseCache.getUseCases();
        log.info(OPERATION_FINISHED);
        return useCases;
    }

    @Override
    public VerificationStartData startVerification(UUID useCaseId) {
        log.debug("Load use-case");
        List<String> attributeList = useCaseCache.getUseCaseById(useCaseId).getSortedAttributes();

        log.debug("Build VerificationRequest");
        VerificationRequest verificationRequest = VerificationRequest.builder()
                .requiredAttributes(attributeList)
                .metaData(VerifierMetaData.builder()
                        .clientName(clientName)
                        .logoUri(logoUri)
                        .build())
                .build();

        log.debug("Start verification Process on Tech-Adapter");
        VerificationResponse verificationResponse = techAdapterClient.startVerification(verificationRequest);

        log.debug("Create QR-Code");
        QrCode qrCode = qrCodeService.create(verificationResponse.getVerificationUrl(), 500);

        verificationId(verificationResponse.getId());
        verificationStatus(verificationResponse.getStatus());
        log.info(OPERATION_FINISHED);
        return VerificationStartData.builder()
                .id(verificationResponse.getId())
                .qrCode(qrCode.getImageData())
                .qrCodeFormat(qrCode.getFormat())
                .build();
    }

    @Override
    public VerificationResponse getVerificationStatus(UUID verificationId) {
        log.debug("Get verification status from Tech-Adapter");
        VerificationResponse verificationResponse = techAdapterClient.getVerificationStatus(verificationId);

        verificationStatus(verificationResponse.getStatus());

        if (isSuccess(verificationResponse.getStatus())) {
            log.info(OPERATION_FINISHED);
        } else if (VerificationStatus.hasFunctionallyFailed(verificationResponse)) {
            errorCode(verificationResponse.getErrorCode());
            log.info(OPERATION_FINISHED);
        } else if (!isPending(verificationResponse.getStatus())) {
            errorCode(verificationResponse.getErrorCode());
            log.error(OPERATION_FINISHED);
        }

        return verificationResponse;
    }
}
