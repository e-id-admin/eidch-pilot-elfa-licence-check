import {Injectable} from '@angular/core';
import {CanActivate, Router} from '@angular/router';
import {PolicyService} from '@app/_services/policy.service';

@Injectable({
  providedIn: 'root'
})
export class PolicyGuard implements CanActivate {
  constructor(private policyService: PolicyService, private router: Router) {}

  canActivate(): boolean {
    if (this.policyService.isPolicyConfirmed()) {
      return true;
    } else {
      this.router.navigateByUrl('/');
      return false;
    }
  }
}
