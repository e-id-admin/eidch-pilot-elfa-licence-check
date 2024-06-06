import {TestBed} from '@angular/core/testing';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {InfoDialogComponent} from './info-dialog.component';
import {UseCase} from '@app/_models';
import {TranslateModule} from '@ngx-translate/core';

describe('InfoDialogComponent', () => {
  let component: InfoDialogComponent;
  let dialogMock: any;
  let dataMock: any;

  beforeEach(() => {
    dialogMock = {close: () => {}};
    dataMock = {item: new UseCase('1', 'Test title', 'Test description', 1, [])};

    TestBed.configureTestingModule({
      imports: [TranslateModule.forRoot()],
      declarations: [InfoDialogComponent],
      // Mock dialog reference and data for Angular Material Dialog
      providers: [
        {provide: MatDialogRef, useValue: dialogMock},
        {provide: MAT_DIALOG_DATA, useValue: dataMock}
      ]
    });

    const fixture = TestBed.createComponent(InfoDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  // Check component creation
  it('should create', () => {
    expect(component).toBeTruthy();
  });

  // Test ngOnInit
  it('should set item from input data on init', () => {
    component.ngOnInit();
    expect(component.item).toEqual(dataMock.item);
  });

  // Test close method
  it('should close the dialog when close method is called', () => {
    jest.spyOn(dialogMock, 'close');
    component.close();
    expect(dialogMock.close).toHaveBeenCalled();
  });
});
