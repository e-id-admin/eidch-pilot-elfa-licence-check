import {TestBed} from '@angular/core/testing';
import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';
import {VerifierService} from './verifier.service';
import {HolderResponse, StartVerificationResponse, VerificationResponse} from '@app/_models';
import {dummyUseCases} from '@app/_services/__mocks__/verifier.service';

describe('VerifierService', () => {
  let service: VerifierService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [VerifierService]
    });

    service = TestBed.inject(VerifierService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  describe('getUseCases', () => {
    it('should return an Observable<UseCase[]>', () => {
      service.getUseCases().subscribe(useCases => {
        expect(useCases.length).toBe(2);
        expect(useCases).toEqual(dummyUseCases);
      });

      const req = httpMock.expectOne('/api/v1/verifier/use-cases');
      expect(req.request.method).toBe('GET');
      req.flush(dummyUseCases);
    });
  });

  // Start the similar tests for startVerificationProcess and getVerificationProcess methods

  describe('startVerificationProcess', () => {
    it('should throw error if useCaseId is null or undefined', () => {
      expect(() => service.startVerificationProcess(null)).toThrow(
        'Required parameter useCaseId was null or undefined when calling startVerificationProcess.'
      );

      expect(() => service.startVerificationProcess(undefined)).toThrow(
        'Required parameter useCaseId was null or undefined when calling startVerificationProcess.'
      );
    });

    it('should return an Observable<StartVerificationResponse>', () => {
      const dummyResponse = new StartVerificationResponse('1', 'QRCODE', 'EAN13');

      service.startVerificationProcess('1').subscribe(response => {
        expect(response).toEqual(dummyResponse);
      });

      const req = httpMock.expectOne('/api/v1/verifier/verify');
      expect(req.request.method).toBe('POST');
      req.flush(dummyResponse);
    });
  });

  describe('getVerificationProcess', () => {
    it('should throw error if id is null or undefined', () => {
      expect(() => service.getVerificationProcess(null)).toThrow(
        'Required parameter verificationId was null or undefined when calling startPolling.'
      );
      expect(() => service.getVerificationProcess(undefined)).toThrow(
        'Required parameter verificationId was null or undefined when calling startPolling.'
      );
    });

    it('should return an Observable<VerificationResponse>', () => {
      const dummyResponse = new VerificationResponse('1', 'SUCCESS', null, null, null, new HolderResponse({}));

      service.getVerificationProcess('1').subscribe(response => {
        expect(response).toEqual(dummyResponse);
      });

      const req = httpMock.expectOne('/api/v1/verifier/verify/1');
      expect(req.request.method).toBe('GET');
      req.flush(dummyResponse);
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
