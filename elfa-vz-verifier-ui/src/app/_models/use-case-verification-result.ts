import {UseCase} from '@app/_models/use-case';
import {VerificationResponse} from '@app/_models/verification-response';

export class UseCaseVerificationResult {
  useCase: UseCase;
  verificationResponse: VerificationResponse;

  constructor(useCase: UseCase, verificationResponse: VerificationResponse) {
    this.useCase = useCase;
    this.verificationResponse = verificationResponse;
  }
}
