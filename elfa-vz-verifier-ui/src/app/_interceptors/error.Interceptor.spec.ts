import {TestBed} from '@angular/core/testing';
import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';
import {HTTP_INTERCEPTORS, HttpHandler, HttpRequest} from '@angular/common/http';
import {ErrorInterceptor} from './error.interceptor';
import {ObENotificationType, ObNotificationService} from '@oblique/oblique';

describe('ErrorInterceptor', () => {
  let interceptor: ErrorInterceptor;
  let notificationService: ObNotificationService;
  let httpMock: HttpTestingController;
  let httpHandlerMock: HttpHandler;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [
        ErrorInterceptor,
        {provide: ObNotificationService, useValue: {error: jest.fn()}},
        {provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true}
      ]
    });

    interceptor = TestBed.inject(ErrorInterceptor);
    notificationService = TestBed.inject(ObNotificationService);
    httpMock = TestBed.inject(HttpTestingController);
    httpHandlerMock = TestBed.inject(HttpHandler);
  });

  afterEach(() => {
    httpMock.verify(); // Make sure that there are no outstanding http calls
  });

  it('should throw an error notification and return neutral object (EMPTY) if the http request fails', () => {
    const errorMsg = 'request failure';

    interceptor.intercept(new HttpRequest('GET', '/test'), httpHandlerMock).subscribe(
      () => {},
      error => {
        expect(notificationService.error).toHaveBeenCalledWith({
          message: errorMsg,
          messageParams: null,
          title: 'Error',
          type: ObENotificationType.ERROR,
          timeout: 10000,
          sticky: false
        });
        expect(error).toBeUndefined();
      }
    );

    const req = httpMock.expectOne('/test');
    expect(req.request.method).toBe('GET');
    req.error(new ErrorEvent('Network error', {message: errorMsg}));
  });

  it('should return successful http request without changes', () => {
    const httpResponse = 'request success';

    interceptor.intercept(new HttpRequest('GET', '/test'), httpHandlerMock).subscribe(response => {
      expect(notificationService.error).toHaveBeenCalledTimes(0);
      expect(response).toBe(httpResponse);
    });

    const req = httpMock.expectOne('/test');
    expect(req.request.method).toBe('GET');
    req.flush(httpResponse);
  });
});
