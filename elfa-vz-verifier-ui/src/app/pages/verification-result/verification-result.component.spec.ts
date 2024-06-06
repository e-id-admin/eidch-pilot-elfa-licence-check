import {ComponentFixture, TestBed} from '@angular/core/testing';

import {VerificationResultComponent} from './verification-result.component';
import {TranslateModule} from '@ngx-translate/core';
import {ObStickyModule} from '@oblique/oblique';

describe('VerificationResultComponent', () => {
  let component: VerificationResultComponent;
  let fixture: ComponentFixture<VerificationResultComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TranslateModule.forRoot(), ObStickyModule],
      declarations: [VerificationResultComponent]
    }).compileComponents();

    fixture = TestBed.createComponent(VerificationResultComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
