import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {PolicyService} from '@app/_services/policy.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  showMessage: boolean = false;
  policyGroup: string = 'policy-group';

  constructor(private router: Router, private policyService: PolicyService) {}

  ngOnInit() {
    if (this.isPolicyConfirmed()) {
      this.router.navigateByUrl('/use-case');
    }
  }

  isPolicyConfirmed(): boolean {
    return this.policyService.isPolicyConfirmed();
  }

  confirmPolicy() {
    if (this.policyService.isPolicyConfirmed()) {
      this.router.navigateByUrl('/use-case');
    } else {
      this.showMessage = true;
      this.policyGroup = 'policy-group-error';
    }
  }

  checkPolicy(checked: boolean) {
    if (checked) {
      this.policyService.setPolicyConfirmed(true);
      this.showMessage = false;
      this.policyGroup = 'policy-group';
    } else {
      this.policyService.setPolicyConfirmed(false);
    }
  }
}
