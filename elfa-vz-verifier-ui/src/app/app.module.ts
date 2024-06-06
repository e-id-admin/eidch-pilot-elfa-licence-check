import {APP_INITIALIZER, CUSTOM_ELEMENTS_SCHEMA, LOCALE_ID, NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {
  multiTranslateLoader,
  OB_BANNER,
  ObAuthenticationModule,
  ObButtonModule,
  ObDocumentMetaService,
  ObExternalLinkModule,
  ObHttpApiInterceptor,
  ObIconModule,
  ObliqueModule,
  ObMasterLayoutConfig,
  ObMasterLayoutModule
} from '@oblique/oblique';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {NgOptimizedImage, registerLocaleData} from '@angular/common';
import localeDECH from '@angular/common/locales/de-CH';
import localeFRCH from '@angular/common/locales/fr-CH';
import localeITCH from '@angular/common/locales/it-CH';
import localeENCH from '@angular/common/locales/en-CH';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {TranslateModule, TranslateService} from '@ngx-translate/core';

import {MatLegacyFormFieldModule} from '@angular/material/legacy-form-field';
import {MatLegacyInputModule} from '@angular/material/legacy-input';
import {MatLegacyButtonModule} from '@angular/material/legacy-button';
import {MatLegacyCardModule} from '@angular/material/legacy-card';
import {MatLegacyTabsModule} from '@angular/material/legacy-tabs';

import {MatIconModule} from '@angular/material/icon';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MatListModule} from '@angular/material/list';
import {MatBadgeModule} from '@angular/material/badge';
import {MatLegacyTableModule} from '@angular/material/legacy-table';
import {MatLegacyPaginatorModule} from '@angular/material/legacy-paginator';
import {MatSortModule} from '@angular/material/sort';
import {MatLegacyTooltipModule} from '@angular/material/legacy-tooltip';
import {MatStepperModule} from '@angular/material/stepper';
import {MatLegacyChipsModule} from '@angular/material/legacy-chips';
import {MatLegacyDialogModule} from '@angular/material/legacy-dialog';
import {UseCaseComponent} from '@app/pages/use-case/use-case.component';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {VerificationResultComponent} from '@app/pages/verification-result/verification-result.component';
import {ScanQrCodeComponent} from '@app/pages/scan-qr-code/scan-qr-code.component';
import {MatDialogModule} from '@angular/material/dialog';
import {PolicyService} from '@app/_services/policy.service';
import {banner} from '@app/core/utils';
import {ErrorInterceptor} from '@app/_interceptors';
import {InfoDialogComponent} from '@app/pages/use-case/info-dialog/info-dialog.component';
import {HomeComponent} from '@app/pages/home/home.component';
import {AppConfigService} from '@app/core/app-config/app-config.service';
import {AppComponent} from '@app/app.component';
import {AppRoutingModule} from '@app/app-routing.module';

registerLocaleData(localeDECH);
registerLocaleData(localeFRCH);
registerLocaleData(localeITCH);
registerLocaleData(localeENCH);

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    UseCaseComponent,
    VerificationResultComponent,
    ScanQrCodeComponent,
    InfoDialogComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    FormsModule,
    HttpClientModule,
    TranslateModule.forRoot(multiTranslateLoader()),
    // Oblique
    ObButtonModule,
    ObMasterLayoutModule,
    ObExternalLinkModule,
    ObIconModule.forRoot(),
    ObliqueModule,
    ObAuthenticationModule.forRoot(),
    // Material
    MatLegacyButtonModule,
    MatLegacyCardModule,
    MatLegacyFormFieldModule,
    MatIconModule,
    MatCheckboxModule,
    MatLegacyInputModule,
    ReactiveFormsModule,
    MatListModule,
    MatBadgeModule,
    MatLegacyTabsModule,
    MatLegacyTableModule,
    MatLegacyPaginatorModule,
    MatSortModule,
    MatLegacyTooltipModule,
    MatStepperModule,
    MatLegacyChipsModule,
    MatLegacyDialogModule,
    MatDialogModule,
    MatToolbarModule,
    NgOptimizedImage
  ],
  providers: [
    // Oblique: show environment in top header
    {
      provide: OB_BANNER,
      useFactory: (appConfigService: AppConfigService) => {
        return banner(appConfigService.appConfig?.environment);
      },
      deps: [AppConfigService]
    },
    {provide: LOCALE_ID, useValue: 'de-CH'},
    {provide: HTTP_INTERCEPTORS, useClass: ObHttpApiInterceptor, multi: true},

    // provider used to create fake backend
    {provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true},

    /* Initialize Services and/or run code on application initialization. */
    {
      provide: APP_INITIALIZER,
      useFactory: () => () => Promise.resolve(),
      multi: true
    },
    PolicyService
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA], // needed for external <header-widget> web component
  bootstrap: [AppComponent]
})
export class AppModule {
  constructor(meta: ObDocumentMetaService, config: ObMasterLayoutConfig, translate: TranslateService) {
    translate.setDefaultLang('de');
    translate.use('de');
    translate.get('i18n.application.title').subscribe((title: string) => {
      meta.setTitle(title);
    });
    config.locale.locales = ['de-CH'];
    config.homePageRoute = '/use-case';
  }
}
