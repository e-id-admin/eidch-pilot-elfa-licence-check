package ch.admin.astra.elfa.trotti.domain.verifier.model;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

@Getter
@Builder
@Jacksonized
public final class VerificationStartData {

    private final UUID id;
    private final byte[] qrCode;
    private final String qrCodeFormat;
}