/**
  * @author Sergio Banegas Cortijo
  * Github: https://github.com/sergiobanegas
*/
import { TestBed, async, inject } from '@angular/core/testing';
import { Response, ResponseOptions, BaseRequestOptions, Http } from '@angular/http';
import { MockBackend, MockConnection } from '@angular/http/testing';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from '@angular/material';
import { FlexLayoutModule } from '@angular/flex-layout';
import 'hammerjs';
import { ToastyService, ToastyConfig, ToastOptions, ToastData } from 'ng2-toasty';

import { UserService } from './user.service';
import { UserAssociationComponent } from './user-association.component';
import { ToastService } from '../../../../../shared/services/toast.service';
import { ShoppingService } from '../../../../shared/services/shopping.service';
import { LocalStorageService } from '../../../../../shared/services/local-storage.service';
import { HTTPService } from '../../../../../shared/services/http.service';


export const UserMock = {
  mobile: 666000002,
  username: 'customer2',
  dni: '1235678Z',
  email: 'user2@user2.com',
  address: 'Calle Goya, 102. Madrid'
}
describe('Component: UserAssociation', () => {

  let userAssociation: UserAssociationComponent;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [ MaterialModule, FlexLayoutModule, FormsModule, BrowserAnimationsModule ],
      declarations: [ UserAssociationComponent ],
      providers: [
        { provide: Router },
        UserService,
        ToastService,
        ShoppingService,
        LocalStorageService,
        MockBackend,
        BaseRequestOptions,
        HTTPService,
        {
          provide: Http,
          useFactory: (backend, options) => new Http(backend, options),
          deps: [ MockBackend, BaseRequestOptions ]
        },
        ToastyService, ToastyConfig, ToastOptions, ToastData
      ]
    });
    let fixture: any = TestBed.createComponent(UserAssociationComponent);
    userAssociation = fixture.componentInstance;
    userAssociation.mobileNumberInput = 666000002;
  }));

  it(`Should associate an User when 'associateUser()' method is called`, inject([MockBackend, ShoppingService], (mockBackend: MockBackend, shoppingService: ShoppingService) => {
    mockBackend.connections.subscribe(conn => {
      conn.mockRespond(new Response(new ResponseOptions({ body: JSON.stringify(UserMock) })));
    });
    userAssociation.associate(new Event('testEvent'));
    expect(shoppingService.getUserMobile()).toBe(666000002);
  }));

  it(`Should disassociate an User when 'disassociateUser()' method is called`, () => {
    userAssociation.disassociate();
    expect(userAssociation.userAssociated).toBeNull();
  });

});
