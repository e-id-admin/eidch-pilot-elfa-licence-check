package ch.admin.astra.elfa.trotti.api;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

@Data
@Jacksonized
@Builder(toBuilder = true)
public class VerificationBeginResponse {

    private UUID id;

    private byte[] qrCode;

    private String qrCodeFormat;

}
