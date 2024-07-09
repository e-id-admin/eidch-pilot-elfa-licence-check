import {Component, OnDestroy, OnInit} from '@angular/core';
import {Attribute, AttributeGroup, UseCase, VerificationResponse} from '@app/_models';
import {UseCaseService} from '@app/_services';
import {Router} from '@angular/router';
import {Subscription, switchMap, timer} from 'rxjs';
import {environment} from '@environments/environment';
import {DateUtils} from '@app/core/utils';

@Component({
  selector: 'app-verification-result',
  templateUrl: './verification-result.component.html',
  styleUrls: ['./verification-result.component.scss']
})
export class VerificationResultComponent implements OnInit, OnDestroy {
  useCase: UseCase;
  verificationResponse: VerificationResponse;
  navigationTimerSubscription: Subscription;

  constructor(private useCaseService: UseCaseService, private router: Router) {}

  ngOnInit() {
    if (!this.useCaseService.getUseCase() || !this.useCaseService.getVerificationResponse()) {
      this.close();
    }
    this.verificationResponse = this.useCaseService.getVerificationResponse();
    this.useCase = this.useCaseService.getUseCase();
    this.startNavigationTimer();
  }

  ngOnDestroy() {
    this.useCaseService.setUseCase(null);
    this.navigationTimerSubscription?.unsubscribe();
  }

  getAttribute(key: string): string | undefined {
    const attribute = this.verificationResponse?.holderResponse?.attributes?.[key];
    return attribute || '---';
  }

  get statusStyle(): string {
    return this.isVcValid() ? 'verification-success' : 'verification-invalid';
  }

  base64image(key: string) {
    return 'data:image/png;base64,' + this.getAttribute(key);
  }

  getSortedAttributeGroups(): AttributeGroup[] {
    return this.useCase.attributeGroups.sort((a, b) => a.order - b.order);
  }

  getSortedAttributes(attributeGroup: AttributeGroup): Attribute[] {
    return attributeGroup.attributes.sort((a, b) => a.order - b.order);
  }

  isVcValid(): boolean {
    if (this.verificationResponse?.holderResponse?.attributes['dateOfExpiration'] !== undefined) {
      const dateOfExpiration = DateUtils.parseDate(
        this.verificationResponse?.holderResponse?.attributes?.dateOfExpiration
      );
      const now = new Date();
      now.setHours(0, 0, 0, 0);
      return dateOfExpiration && dateOfExpiration >= now;
    }
    return false;
  }

  displayVc(): boolean {
    return this.useCaseService.isVcValid();
  }

  displayTimeout(): boolean {
    return this.useCaseService.isTimeout();
  }

  displayRejected(): boolean {
    return this.useCaseService.isRejected();
  }

  displayInvalid(): boolean {
    return this.useCaseService.isVcInvalid();
  }

  displayGroup(group: AttributeGroup) {
    return group.name !== 'image';
  }

  close() {
    this.router.navigate(['/use-case']);
  }

  private startNavigationTimer(): void {
    this.navigationTimerSubscription = timer(environment.navigationDelay)
      .pipe(switchMap(() => this.router.navigate(['/use-case'])))
      .subscribe();
  }
}
