import {Injectable} from '@angular/core';
import {UseCase, VerificationResponse} from '@app/_models';

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
}
