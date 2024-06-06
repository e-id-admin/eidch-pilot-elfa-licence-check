import {ComponentFixture, TestBed} from '@angular/core/testing';
import {AppComponent} from './app.component';
import {AppConfigService} from '@app/core/app-config/app-config.service';
import {of} from 'rxjs';
import {TranslateModule} from '@ngx-translate/core';
import {RouterTestingModule} from '@angular/router/testing';

describe('AppComponent', () => {
  let component: AppComponent;
  let fixture: ComponentFixture<AppComponent>;
  let mockAppConfigService!: {loadAppConfig: jest.Mock};

  beforeEach(async () => {
    mockAppConfigService = {
      loadAppConfig: jest.fn().mockReturnValue(
        of({
          version: '1.2.0',
          environment: 'LOCAL'
        })
      )
    };

    await TestBed.configureTestingModule({
      imports: [TranslateModule.forRoot(), RouterTestingModule.withRoutes([])],
      declarations: [AppComponent],
      providers: [{provide: AppConfigService, useValue: mockAppConfigService}]
    }).compileComponents();

    fixture = TestBed.createComponent(AppComponent);
    component = fixture.componentInstance;
  });

  it('should create the app', () => {
    expect(component).toBeTruthy();
  });

  it('should have current year as new Date().getFullYear()', () => {
    let currentYear = new Date().getFullYear();
    fixture.detectChanges();
    expect(component.currentYear).toEqual(currentYear);
  });

  it('should set banner according to the environment', () => {
    let env = 'LOCAL';
    fixture.detectChanges();
    expect(component.banner.text).toBe(env);
  });
});
