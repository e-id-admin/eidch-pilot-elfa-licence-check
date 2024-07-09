import {ComponentFixture, TestBed} from '@angular/core/testing';
import {HomeComponent} from '@app/pages/home/home.component';
import {PolicyService} from '@app/_services/policy.service';
import {Router} from '@angular/router';
import {TranslateModule} from '@ngx-translate/core';
import {MatCheckboxModule} from '@angular/material/checkbox';

// jest.mock('../../_services/policy.service');
jest.mock('@angular/router');

describe('HomeComponent', () => {
  let component: HomeComponent;
  let fixture: ComponentFixture<HomeComponent>;
  let policyServiceStub: {isPolicyConfirmed: any; setPolicyConfirmed?: jest.Mock<any, any>};
  let routerStub: {navigateByUrl: jest.Mock<any, any>};

  beforeEach(async () => {
    policyServiceStub = {
      isPolicyConfirmed: jest.fn(),
      setPolicyConfirmed: jest.fn()
    };

    routerStub = {
      navigateByUrl: jest.fn()
    };

    await TestBed.configureTestingModule({
      imports: [TranslateModule.forRoot(), MatCheckboxModule],
      declarations: [HomeComponent],
      providers: [
        {provide: PolicyService, useValue: policyServiceStub},
        {provide: Router, useValue: routerStub}
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(HomeComponent);
    component = fixture.componentInstance;
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should navigate to /use-case if policy is already confirmed', () => {
    policyServiceStub.isPolicyConfirmed.mockReturnValue(true);

    // simulate first time loading
    component.ngOnInit();

    expect(routerStub.navigateByUrl).toHaveBeenCalledWith('/use-case');
  });

  it('should not navigate if policy is not confirmed', () => {
    expect(routerStub.navigateByUrl).toHaveBeenCalledTimes(0);
  });

  it('should call policyService.isPolicyConfirmed when isPolicyConfirmed is called', () => {
    component.isPolicyConfirmed();
    expect(policyServiceStub.isPolicyConfirmed).toHaveBeenCalled();
  });

  it('should navigate to /use-case and not show error message when confirmPolicy is called and policy is confirmed', () => {
    policyServiceStub.isPolicyConfirmed.mockReturnValue(true);

    component.confirmPolicy();

    expect(routerStub.navigateByUrl).toHaveBeenCalledWith('/use-case');
    expect(component.showMessage).toBe(false);
  });

  it('should show error message and not navigate when confirmPolicy is called and policy is not confirmed', () => {
    component.confirmPolicy();

    expect(component.showMessage).toBe(true);
    expect(component.policyGroup).toBe('policy-group-error');
    expect(routerStub.navigateByUrl).toHaveBeenCalledTimes(0);
  });

  it('should set policy confirmation and hide error message when checkPolicy is called with checked state', () => {
    component.checkPolicy(true);

    expect(policyServiceStub.setPolicyConfirmed).toHaveBeenCalledWith(true);
    expect(component.showMessage).toBe(false);
    expect(component.policyGroup).toBe('policy-group');
  });

  it('should remove policy confirmation when checkPolicy is called with unchecked state', () => {
    component.checkPolicy(false);

    expect(policyServiceStub.setPolicyConfirmed).toHaveBeenCalledWith(false);
  });
});
