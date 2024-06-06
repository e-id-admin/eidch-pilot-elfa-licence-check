package ch.admin.astra.elfa.trotti.domain.qrcode;

import ch.admin.astra.elfa.trotti.domain.exception.BusinessException;
import ch.admin.astra.elfa.trotti.domain.qrcode.model.QrCode;

/**
 * Service interface for generating QR codes.
 */
public interface QrCodeService {

    /**
     * Creates a {@link QrCode} from given inputs.
     *
     * @param data to convert to an image
     * @param size image size
     * @return {@link QrCode} containing the image, mime-type and format
     * @throws BusinessException when an unexpected error occurs during conversion
     */
    QrCode create(String data, int size);

}
