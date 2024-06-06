import {Component, OnDestroy, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {StartVerificationResponse, UseCase, VerificationResponse} from '@app/_models';
import {UseCaseService, VerifierService} from '@app/_services';
import {environment} from '@environments/environment';
import {ObHttpApiInterceptorConfig} from '@oblique/oblique';
import {interval, startWith, Subscription, switchMap} from 'rxjs';
import {TimerService} from '@app/_services/timer.service';

@Component({
  selector: 'app-scan-qr-code',
  templateUrl: './scan-qr-code.component.html',
  styleUrls: ['./scan-qr-code.component.scss']
})
export class ScanQrCodeComponent implements OnInit, OnDestroy {
  useCase: UseCase;
  response: StartVerificationResponse;

  intervalSubscription: Subscription;

  constructor(
    private verifierService: VerifierService,
    private router: Router,
    private obHttpApiInterceptorConfig: ObHttpApiInterceptorConfig,
    private useCaseService: UseCaseService,
    private timerService: TimerService
  ) {}

  ngOnInit() {
    // deactivate spinner
    this.obHttpApiInterceptorConfig.api.spinner = false;

    this.useCase = this.useCaseService.getUseCase();
    if (this.useCase) {
      this.verifierService.startVerificationProcess(this.useCase.id).subscribe({
        next: (result: StartVerificationResponse) => {
          this.response = result;
          // start intervall and poll every 4 seconds
          this.startPolling();
          this.timerService.startTimeoutTimer();
          this.timerService.subscribeToTimer({
            next: () => this.handleResponse()(VerificationResponse.createTimeoutResponse(this.useCase.id))
          });
        }
      });
    } else {
      this.close();
    }
  }

  ngOnDestroy(): void {
    this.obHttpApiInterceptorConfig.api.spinner = true;
    this.intervalSubscription?.unsubscribe();
    this.timerService.stopTimer();
  }

  private startPolling() {
    this.intervalSubscription = interval(environment.pollingInterval)
      .pipe(
        startWith(0),
        switchMap(() => this.verifierService.getVerificationProcess(this.response.id))
      )
      .subscribe({
        next: this.handleResponse(),
        error: this.handleError()
      });
  }

  handleResponse() {
    return (response: VerificationResponse) => {
      if (response.status !== 'PENDING') {
        this.useCaseService.setVerificationResponse(response);
        this.goToVerification();
      }
    };
  }

  handleError() {
    return (error: VerificationResponse) => {
      if (error.status !== 'PENDING') {
        this.close();
      }
    };
  }
  goToVerification() {
    this.router.navigate(['/verification-result']);
  }

  close() {
    this.useCaseService.setUseCase(null);
    this.router.navigate(['/use-case']);
  }

  get base64image() {
    return 'data:image/' + this.response.qrCodeFormat + ';base64,' + this.response.qrCode;
  }
}
