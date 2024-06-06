package ch.admin.astra.elfa.trotti.domain.qrcode.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QrCode {

    private final String format;
    private final String mime;
    private final byte[] imageData;

}
