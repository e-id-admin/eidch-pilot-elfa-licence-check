import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ObIBanner} from '@oblique/oblique/lib/utilities.model';
import {VERSION} from '@environments/environment';
import {banner} from '@app/core/utils';
import {AppConfigService} from '@app/core/app-config/app-config.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class AppComponent implements OnInit {
  banner: ObIBanner;
  appVersion: any;
  currentYear: number;
  supportItems = [
    {link: 'https://findmind.ch/c/Ce39-nUQL', label: 'i18n.support.feedback'},
    {link: 'https://www.eid.admin.ch/de/hilfe-support', label: 'i18n.support.help'},
    {link: 'https://forms.eid.admin.ch/elfa', label: 'i18n.support.contact'},
    {link: 'https://www.eid.admin.ch/de/pilotprojekte', label: 'i18n.support.more-information'}
  ];

  constructor(private appConfigService: AppConfigService) {}

  ngOnInit(): void {
    this.appVersion = VERSION;
    this.currentYear = new Date().getFullYear();

    this.appConfigService.loadAppConfig().subscribe(({environment}) => {
      const env = environment || 'PROD';
      this.banner = banner(env);
    });
  }
}
