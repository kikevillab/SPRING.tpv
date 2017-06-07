/**
  * @author Sergio Banegas Cortijo
  * Github: https://github.com/sergiobanegas 
*/
import { TestBed, async, inject } from '@angular/core/testing';
import { HttpModule, Response, ResponseOptions, BaseRequestOptions, Http } from '@angular/http';
import { MockBackend, MockConnection } from '@angular/http/testing';

import { CartProduct } from '../../../../shared/models/cart-product.model';
import { PrintService } from './print.service';

import { HTTPService } from '../../../../../shared/services/http.service';


describe('Service: PrintService', () => {

  let printService: PrintService;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [ HttpModule ],
      providers: [ 
        PrintService,
        MockBackend,
        BaseRequestOptions,
        HTTPService,
        {
          provide: Http,
          useFactory: (backend, options) => new Http(backend, options),
          deps: [ MockBackend, BaseRequestOptions ]
        }
      ]
    });
    printService = TestBed.get(PrintService);
  }));

});
