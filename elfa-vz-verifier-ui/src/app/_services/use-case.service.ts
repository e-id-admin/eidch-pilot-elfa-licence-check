import {Injectable} from '@angular/core';
import {UseCase, VerificationResponse} from '@app/_models';
import {functionalErrorCodes} from '@app/core/utils/verification-utils';

@Injectable({
  providedIn: 'root'
})
export class UseCaseService {
  private useCase: UseCase;
  private verificationResponse: VerificationResponse;

  setUseCase(useCase: UseCase) {
    this.useCase = useCase;
  }

  getUseCase(): UseCase {
    return this.useCase;
  }

  setVerificationResponse(response: VerificationResponse) {
    this.verificationResponse = response;
  }

  getVerificationResponse(): VerificationResponse {
    return this.verificationResponse;
  }

  isVcValid(): boolean {
    return this.verificationResponse?.status === 'SUCCESS';
  }

  isTimeout(): boolean {
    return (
      this.verificationResponse?.status === 'FAILED' && this.verificationResponse?.errorCode === 'verification_expired'
    );
  }

  isRejected(): boolean {
    return this.verificationResponse?.status === 'FAILED' && this.verificationResponse?.errorCode === 'client_rejected';
  }

  isVcInvalid(): boolean {
    return (
      this.verificationResponse?.status === 'FAILED' &&
      functionalErrorCodes.includes(this.verificationResponse?.errorCode)
    );
  }
}
