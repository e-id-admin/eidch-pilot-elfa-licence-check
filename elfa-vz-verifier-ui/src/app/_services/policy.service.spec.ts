import {CookieService} from 'ngx-cookie-service';
import {PolicyService} from './policy.service';
import {CookieConstants} from '@app/core/utils';
import {TestBed} from '@angular/core/testing';

describe('Policy Service', () => {
  let service: PolicyService;
  let mockCookieService!: {get: jest.Mock; set: jest.Mock; delete: jest.Mock};

  beforeEach(() => {
    mockCookieService = {
      get: jest.fn(),
      set: jest.fn(),
      delete: jest.fn()
    };

    TestBed.configureTestingModule({
      providers: [{provide: CookieService, useValue: mockCookieService}]
    });
    service = TestBed.inject(PolicyService);
  });

  describe('isPolicyConfirmed', () => {
    it('should return true if cookieService.get returns "true"', () => {
      mockCookieService.get.mockReturnValue(CookieConstants.BOOL_TRUE);
      expect(service.isPolicyConfirmed()).toEqual(true);
    });

    it('should return true if cookieService.get returns not "true"', () => {
      mockCookieService.get.mockReturnValue('false');
      expect(service.isPolicyConfirmed()).toEqual(false);
    });
  });

  describe('setPolicyConfirmed', () => {
    it('should call cookieService.set with correct params if value is true', () => {
      service.setPolicyConfirmed(true);
      expect(mockCookieService.set).toHaveBeenCalledWith(
        CookieConstants.POLICY_CONFIRMED,
        CookieConstants.BOOL_TRUE,
        expect.anything() // the expiry date
      );
    });

    it('should call cookieService.delete if value is false', () => {
      service.setPolicyConfirmed(false);
      expect(mockCookieService.delete).toHaveBeenCalled();
    });
  });
});
