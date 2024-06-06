export class StartVerificationResponse {
  id: string;
  qrCode: string;
  qrCodeFormat: string;

  constructor(id: string, qrCode: string, qrCodeFormat: string) {
    this.id = id;
    this.qrCode = qrCode;
    this.qrCodeFormat = qrCodeFormat;
  }
}
