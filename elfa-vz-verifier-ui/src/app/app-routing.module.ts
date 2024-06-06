import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ObUnknownRouteModule} from '@oblique/oblique';
import {ScanQrCodeComponent} from '@app/pages/scan-qr-code/scan-qr-code.component';
import {VerificationResultComponent} from '@app/pages/verification-result/verification-result.component';
import {PolicyGuard} from '@app/_guards/policy.guard';
import {PolicyService} from '@app/_services/policy.service';
import {HomeComponent} from '@app/pages/home/home.component';
import {UseCaseComponent} from '@app/pages/use-case/use-case.component';

const routes: Routes = [
  {path: '', component: HomeComponent, pathMatch: 'full'},
  {
    path: 'use-case',
    component: UseCaseComponent,
    canActivate: [PolicyGuard],
    pathMatch: 'full'
  },
  {
    path: 'scan-qr-code',
    component: ScanQrCodeComponent,
    canActivate: [PolicyGuard],
    pathMatch: 'full'
  },
  {
    path: 'verification-result',
    component: VerificationResultComponent,
    canActivate: [PolicyGuard],
    pathMatch: 'full'
  },
  {path: '**', redirectTo: ''}
];

@NgModule({
  imports: [RouterModule.forRoot(routes), ObUnknownRouteModule],
  exports: [RouterModule],
  providers: [PolicyService]
})
export class AppRoutingModule {}
