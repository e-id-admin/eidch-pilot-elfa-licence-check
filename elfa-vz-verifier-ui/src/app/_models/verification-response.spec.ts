import {VerificationResponse} from './verification-response';

describe('VerificationResponse', () => {
  it('should create an instance', () => {
    expect(new VerificationResponse(null, null, null, null, null, null)).toBeTruthy();
  });

  it('createTimeoutResponse should return a VerificationResponse with timeout status', () => {
    const timeoutResponse = VerificationResponse.createTimeoutResponse('123');
    expect(timeoutResponse).toBeInstanceOf(VerificationResponse);
    expect(timeoutResponse.id).toBe('123');
    expect(timeoutResponse.status).toBe('FAILED');
    expect(timeoutResponse.errorCode).toBe('verification_expired');
  });
});
