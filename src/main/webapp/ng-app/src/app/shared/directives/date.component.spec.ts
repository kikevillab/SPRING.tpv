/**
  * @author Sergio Banegas Cortijo
  * Github: https://github.com/sergiobanegas 
*/
import { TestBed, async, inject } from '@angular/core/testing';

import { DateComponent } from './date.component';

describe('Component: DateComponent', () => {

  let fixture, dateComponent, element, de;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DateComponent]
    });
    fixture = TestBed.createComponent(DateComponent);
    dateComponent = fixture.componentInstance;
    element = fixture.nativeElement;
    de = fixture.debugElement;
  });

  it(`Date should update`, done => {
    let date1:Date = dateComponent.date;
    fixture.detectChanges();
    setTimeout(() => {
        let date2:Date = dateComponent.date;
        expect(date1).not.toBe(date2);
        done();
    }, 1000);
  });

});
