import { TestBed, async, inject } from '@angular/core/testing';
import 'hammerjs';
import { Response, ResponseOptions, BaseRequestOptions, Http } from '@angular/http';
import { MockBackend, MockConnection } from '@angular/http/testing';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from '@angular/material';
import { FlexLayoutModule } from '@angular/flex-layout';
import { ToastyService, ToastyConfig, ToastOptions, ToastData } from 'ng2-toasty';

import { OpenCashierComponent } from './open-cashier.component';

import { CashierService } from '../shared/services/cashier.service';
import { ToastService } from '../../shared/services/toast.service';
import { HTTPService } from '../../shared/services/http.service';

describe('Component: OpenCashierComponent', () => {

  let open: OpenCashierComponent;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [ MaterialModule, FlexLayoutModule, FormsModule, BrowserAnimationsModule ],
      declarations: [OpenCashierComponent],
      providers: [
        { provide: Router },
        ToastService,
        CashierService,
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
    let fixture: any = TestBed.createComponent(OpenCashierComponent);
    open = fixture.componentInstance;
  }));

});
