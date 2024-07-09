import {TestBed} from '@angular/core/testing';
import {UseCaseService} from './use-case.service';
import {UseCase, VerificationResponse} from '@app/_models';

describe('UseCaseService', () => {
  let useCaseService: UseCaseService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    useCaseService = TestBed.inject(UseCaseService);
  });

  describe('setUseCase', () => {
    it('should set the use case', () => {
      const useCase = new UseCase('1', 'Test', 'Description', 1, []);
      useCaseService.setUseCase(useCase);
      expect(useCaseService.getUseCase()).toEqual(useCase);
    });
  });

  describe('setVerificationResponse', () => {
    it('should set the verification response', () => {
      const response = new VerificationResponse('1', 'SUCCESS', null, null, null, null);
      useCaseService.setVerificationResponse(response);
      expect(useCaseService.getVerificationResponse()).toEqual(response);
    });
  });
});
