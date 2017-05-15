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

import { CloseCashierComponent } from './close-cashier.component';

import { CashierService } from '../shared/services/cashier.service';
import { ToastService } from '../../shared/services/toast.service';
import { HTTPService } from '../../shared/services/http.service';

describe('Component: CloseCashierComponent', () => {

  let closeCashier: CloseCashierComponent;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [ MaterialModule, FlexLayoutModule, FormsModule, BrowserAnimationsModule ],
      declarations: [ CloseCashierComponent ],
      providers: [
        { provide: Router },
        ToastService,
        MockBackend,
        CashierService,
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
    let fixture: any = TestBed.createComponent(CloseCashierComponent);
    closeCashier = fixture.componentInstance;
  }));

});
