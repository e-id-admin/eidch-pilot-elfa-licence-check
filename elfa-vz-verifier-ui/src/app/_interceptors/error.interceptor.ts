import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {EMPTY, Observable} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {ObENotificationType, ObNotificationService} from '@oblique/oblique';

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
  constructor(private notificationService: ObNotificationService) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    return next.handle(request).pipe(
      catchError((err, _caught) => {
        let details = null;
        if (err.error?.details?.length > 0) {
          details = err.error.details.map((element: string) => element).join('\n');
        }

        this.notificationService.error({
          message: err.error.error,
          messageParams: details,
          title: 'Error',
          type: ObENotificationType.ERROR,
          timeout: 10000,
          sticky: false
        });

        return EMPTY;
      })
    );
  }
}
