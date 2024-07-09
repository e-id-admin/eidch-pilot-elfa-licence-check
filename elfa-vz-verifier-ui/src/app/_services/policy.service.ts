import {Injectable} from '@angular/core';
import {CookieService} from 'ngx-cookie-service';
import {CookieConstants} from '@app/core/utils';
import {environment} from '@environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PolicyService {
  constructor(private cookieService: CookieService) {}

  isPolicyConfirmed(): boolean {
    return this.cookieService.get(CookieConstants.POLICY_CONFIRMED) === CookieConstants.BOOL_TRUE;
  }

  setPolicyConfirmed(value: boolean): void {
    if (value) {
      const expirationDate = new Date();
      expirationDate.setDate(expirationDate.getDate() + environment.cookieValidity);
      this.cookieService.set(CookieConstants.POLICY_CONFIRMED, CookieConstants.BOOL_TRUE, expirationDate);
    } else {
      this.cookieService.delete(CookieConstants.POLICY_CONFIRMED);
    }
  }
}
