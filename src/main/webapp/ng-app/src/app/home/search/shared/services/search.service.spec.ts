/**
  * @author Sergio Banegas Cortijo
  * Github: https://github.com/sergiobanegas 
*/
import { TestBed, async, inject } from '@angular/core/testing';
import { HttpModule, Response, ResponseOptions, BaseRequestOptions, Http } from '@angular/http';
import { MockBackend, MockConnection } from '@angular/http/testing';
import { FormsModule } from '@angular/forms';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MaterialModule } from '@angular/material';

import { SearchService } from './search.service';

import { HTTPService } from '../../../../shared/services/http.service';
import { LocalStorageService } from '../../../../shared/services/local-storage.service';

export const MockCategory = {
  id: 1234,
  name: "category1",
  code: null,
  image: 'image'
}

describe('Service: SearchService', () => {

  let searchService: SearchService;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpModule,
        FormsModule,
        FlexLayoutModule,
        MaterialModule
      ],
      providers: [ 
        SearchService,
        MockBackend,
        BaseRequestOptions,
        HTTPService,
        LocalStorageService,
        {
          provide: Http,
          useFactory: (backend, options) => new Http(backend, options),
          deps: [ MockBackend, BaseRequestOptions ]
        }
      ]
    });
    searchService = TestBed.get(SearchService);
  }));

  it(`Should asign the parent category when 'getCategoryContent()' is called`, inject([MockBackend], (mockBackend: MockBackend) => {  
      mockBackend.connections.subscribe((conn: MockConnection) => {
        conn.mockRespond(new Response(new ResponseOptions({ body: JSON.stringify(MockCategory) })));
      });
      searchService.getCategoryContent(1234).then(()=>{
        expect(searchService.getParentCategory()).toBe(1234);
      });
   }));

});
