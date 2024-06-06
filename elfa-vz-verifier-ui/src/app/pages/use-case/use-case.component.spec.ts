import {ComponentFixture, TestBed} from '@angular/core/testing';

import {UseCaseComponent} from './use-case.component';
import {TranslateModule} from '@ngx-translate/core';
import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';
import {MatDialog} from '@angular/material/dialog';
import {ObStickyModule} from '@oblique/oblique';

describe('UseCaseSelectorComponent', () => {
  let component: UseCaseComponent;
  let fixture: ComponentFixture<UseCaseComponent>;
  let httpMock: HttpTestingController;
  let dialogMock: any;

  beforeEach(async () => {
    dialogMock = {close: () => {}};

    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, TranslateModule.forRoot(), ObStickyModule],
      declarations: [UseCaseComponent],
      providers: [{provide: MatDialog, useValue: dialogMock}]
    }).compileComponents();

    fixture = TestBed.createComponent(UseCaseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
