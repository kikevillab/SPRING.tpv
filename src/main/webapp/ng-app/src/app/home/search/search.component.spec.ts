import { TestBed, async, inject } from '@angular/core/testing';
import 'hammerjs';
import { Response, ResponseOptions, BaseRequestOptions, Http } from '@angular/http';
import { MockBackend, MockConnection } from '@angular/http/testing';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/of';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from '@angular/material';
import { FlexLayoutModule } from '@angular/flex-layout';
import { NgxDatatableModule } from '@swimlane/ngx-datatable';
import { ToastyService, ToastyConfig, ToastOptions, ToastData } from 'ng2-toasty';

import { SearchComponent } from './search.component';

import { ToastService } from '../../shared/toast.service';
import { ShoppingCartService } from '../shared/shopping-cart.service';
import { LocalStorageService } from '../../shared/local-storage.service';
import { TPVService } from '../../shared/tpv.service';


describe('Component: SearchComponent', () => {

  let fixture, search, element, de;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [MaterialModule, FlexLayoutModule, NgxDatatableModule, FormsModule, BrowserAnimationsModule],
      declarations: [SearchComponent],
      providers: [
      {provide: Router},
      ToastService,
      ShoppingCartService,
      LocalStorageService,
      TPVService, 
      MockBackend,
      BaseRequestOptions,
      {
        provide: Http,
        useFactory: (backend, options) => new Http(backend, options),
        deps: [MockBackend, BaseRequestOptions]
      },
      ToastyService, ToastyConfig, ToastOptions, ToastData
      ]
    });
    fixture = TestBed.createComponent(SearchComponent);
    search = fixture.componentInstance;
    element = fixture.nativeElement;
    de = fixture.debugElement;
  }));

});
