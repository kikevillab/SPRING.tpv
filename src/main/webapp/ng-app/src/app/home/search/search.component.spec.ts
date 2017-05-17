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

import { SearchComponent } from './search.component';

import { ToastService } from '../../shared/services/toast.service';
import { ShoppingService } from '../shared/services/shopping.service';
import { LocalStorageService } from '../../shared/services/local-storage.service';
import { HTTPService } from '../../shared/services/http.service';


describe('Component: SearchComponent', () => {

  let search: SearchComponent;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [ MaterialModule, FlexLayoutModule, FormsModule, BrowserAnimationsModule ],
      declarations: [ SearchComponent ],
      providers: [
        { provide: Router },
        ToastService,
        ShoppingCartService,
        LocalStorageService,
        HTTPService, 
        MockBackend,
        BaseRequestOptions,
        {
          provide: Http,
          useFactory: (backend, options) => new Http(backend, options),
          deps: [ MockBackend, BaseRequestOptions ]
        },
        ToastyService, ToastyConfig, ToastOptions, ToastData
      ]
    });
    let fixture: any = TestBed.createComponent(SearchComponent);
    search = fixture.componentInstance;
  }));

});
