import {Component, OnDestroy, OnInit, ViewEncapsulation} from '@angular/core';
import {UseCase} from '@app/_models';
import {UseCaseService, VerifierService} from '@app/_services';
import {Router} from '@angular/router';
import {Subscription} from 'rxjs';
import {MatDialog, MatDialogConfig} from '@angular/material/dialog';
import {InfoDialogComponent} from '@app/pages/use-case/info-dialog/info-dialog.component';

@Component({
  selector: 'app-use-case',
  templateUrl: './use-case.component.html',
  styleUrls: ['./use-case.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class UseCaseComponent implements OnInit, OnDestroy {
  useCases: UseCase[];
  useCaseSubscription: Subscription;

  constructor(
    private useCaseService: UseCaseService,
    private router: Router,
    private readonly verifierService: VerifierService,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.useCaseSubscription = this.verifierService.getUseCases().subscribe({
      next: useCases => {
        useCases.sort((a, b) => a.order - b.order);
        this.useCases = useCases;
      }
    });
  }

  ngOnDestroy(): void {
    this.useCaseSubscription.unsubscribe();
  }

  createVerificationRequest(useCase: UseCase): void {
    this.useCaseService.setUseCase(useCase);
    this.router.navigate(['/scan-qr-code']);
  }

  openInfoDialog(useCase: UseCase) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.maxWidth = window.innerWidth <= 600 ? '90vw' : '35vw';
    dialogConfig.maxHeight = '90vh';
    dialogConfig.autoFocus = false;
    dialogConfig.data = {item: useCase};

    this.dialog.open(InfoDialogComponent, dialogConfig);
  }
}
