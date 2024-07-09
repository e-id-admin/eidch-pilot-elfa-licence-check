import {HolderResponse} from '@app/_models/holder-response';

export class VerificationResponse {
  id: string;
  status: 'PENDING' | 'SUCCESS' | 'FAILED';
  errorCode: string;
  errorDescription: string;
  verificationUrl: string;
  holderResponse: HolderResponse;

  constructor(
    id: string,
    status: 'PENDING' | 'SUCCESS' | 'FAILED',
    error_code: string,
    error_description: string,
    verification_url: string,
    holder_response: HolderResponse
  ) {
    this.id = id;
    this.status = status;
    this.errorCode = error_code;
    this.errorDescription = error_description;
    this.verificationUrl = verification_url;
    this.holderResponse = holder_response;
  }

  public static createTimeoutResponse(id: string): VerificationResponse {
    return new VerificationResponse(id, 'FAILED', 'verification_expired', null, null, null);
  }
}
