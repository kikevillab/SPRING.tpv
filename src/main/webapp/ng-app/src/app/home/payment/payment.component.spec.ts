import { TestBed, async, inject } from '@angular/core/testing';
import 'hammerjs';
import { Response, ResponseOptions, BaseRequestOptions, Http } from '@angular/http';
import { MockBackend, MockConnection } from '@angular/http/testing';
import {browser} from 'protractor'
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/of';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from '@angular/material';
import { FlexLayoutModule } from '@angular/flex-layout';
import { NgxDatatableModule } from '@swimlane/ngx-datatable';
import { ToastyService, ToastyConfig, ToastOptions, ToastData } from 'ng2-toasty';

import { PaymentComponent } from './payment.component';
import { User } from '../shared/user';
import { ToastService } from '../../shared/toast.service';
import { ShoppingCartService } from '../shared/shopping-cart.service';
import { LocalStorageService } from '../../shared/local-storage.service';
import { TPVService } from '../../shared/tpv.service';


export const UserMock = {
  mobile: 666000002,
  username: 'customer2',
  dni: '1235678Z',
  email: 'user2@user2.com',
  address: 'Calle Goya, 102. Madrid'
}
describe('Component: PaymentComponent', () => {

  let fixture, payment, element, de;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [MaterialModule, FlexLayoutModule, NgxDatatableModule, FormsModule, BrowserAnimationsModule],
      declarations: [PaymentComponent],
      providers: [
      {provide: Router},
      ToastService,
      ShoppingCartService,
      LocalStorageService,
      MockBackend,
      BaseRequestOptions,
      TPVService, 
      {
        provide: Http,
        useFactory: (backend, options) => new Http(backend, options),
        deps: [MockBackend, BaseRequestOptions]
      },
      ToastyService, ToastyConfig, ToastOptions, ToastData
      ]
    });
    fixture = TestBed.createComponent(PaymentComponent);
    payment = fixture.componentInstance;
    element = fixture.nativeElement;
    de = fixture.debugElement;
    payment.mobileNumberInput = 666000002;
  }));

  it(`Should associate an User when 'associateUser()' method is called`, inject([MockBackend, ShoppingCartService], (mockBackend: MockBackend, shoppingCartService: ShoppingCartService) => {
    mockBackend.connections.subscribe(conn => {
      conn.mockRespond(new Response(new ResponseOptions({ body: JSON.stringify(UserMock) })));
    });
    payment.associateUser(new Event('testEvent'));
    expect(shoppingCartService.getUserMobile()).toBe(666000002);
  }));

  it(`Should disassociate an User when 'disassociateUser()' method is called`, inject([MockBackend, ShoppingCartService], (mockBackend: MockBackend, shoppingCartService: ShoppingCartService) => {
    payment.disassociateUser();
    expect(payment.userAssociated).toBeNull();    
  }));


});
