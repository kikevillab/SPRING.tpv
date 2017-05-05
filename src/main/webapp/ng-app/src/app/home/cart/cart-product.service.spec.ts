import { TestBed, async, inject } from '@angular/core/testing';
import { HttpModule, Response, ResponseOptions, BaseRequestOptions, Http } from '@angular/http';
import { MockBackend, MockConnection } from '@angular/http/testing';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/of';

import { CartProduct } from './cart-product';

import { CartProductService } from './cart-product.service';
import { LocalStorageService } from '../../shared/local-storage.service';
import { TPVService } from '../../shared/tpv.service';

export const MockProduct = {
  id: 0,
  code: 'article0',
  description: 'article0',
  retailPrice: 20.00,
  discontinued: false
}

export const MockTicket = {
  ticketReference: '12341234'
}

export class TPVServiceMock {
  public requestGet(url:string) {
    let response = new ResponseOptions({
      body: JSON.stringify(MockProduct)
    });
    return Observable.of(new Response(response));
  }
  public requestPost(url:string, object:Object) {
    let response = new ResponseOptions({
      body: JSON.stringify(MockTicket)
    });
    return Observable.of(new Response(response));
  }
}

describe('Service: CartProductService', () => {

  let product_code:string = 'article0';

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [HttpModule],
      providers: [ 
      CartProductService,
      LocalStorageService,
      MockBackend,
      BaseRequestOptions,
      TPVService,
      {
        provide: Http,
        useFactory: (backend, options) => new Http(backend, options),
        deps: [MockBackend, BaseRequestOptions]
      }
      ]
    });
    let cartProductService:CartProductService = TestBed.get(CartProductService);
  }));

  afterEach(inject([CartProductService], (cartProductService: CartProductService) => {
    cartProductService.clear();
  }));

  it(`Should add product to cart when 'addProduct()' is called`, inject([CartProductService, MockBackend], (cartProductService: CartProductService, mockBackend:MockBackend) => {
    let found:boolean = false;
    mockBackend.connections.subscribe(conn => {
      conn.mockRespond(new Response(new ResponseOptions({ body: JSON.stringify(MockProduct) })));
    });
    cartProductService.addProduct(product_code);

    cartProductService.getCartProducts().forEach(cartProduct => {
      if (cartProduct.productCode == product_code) found = true;
    });
    expect(found).toBe(true);
  }));

  it(`Should remove product with code '${product_code}' of cart`, inject([CartProductService], (cartProductService: CartProductService) => {
    cartProductService.addProduct(product_code);
    cartProductService.removeProduct(new CartProduct(product_code, 'DESCRIPTION1', 12.21));
    let found:boolean = false;
    cartProductService.getCartProducts().forEach(cartProduct => {
      if (cartProduct.productCode === product_code) found = true;
    });
    expect(found).toBe(false);
  }));

  it(`Should update the product data when 'updateProduct()' method is called`, inject([CartProductService], (cartProductService: CartProductService) => {
    let added = cartProductService.addProduct(product_code);
    added.then(added=>{
      let cartProduct:CartProduct = new CartProduct(product_code, 'DESCRIPTION1', 12.21);
      cartProduct.delivered = false;
      cartProductService.updateProduct(cartProduct);
      expect(cartProductService.getCartProducts()[0].delivered).toBe(false);
    });
    
  }));

  it(`Should clear the cart when 'clear()' method is called`, inject([CartProductService], (cartProductService: CartProductService) => {
    cartProductService.addProduct(product_code);
    cartProductService.clear();
    expect(cartProductService.getCartProducts().length).toBe(0);
  }));

  it(`Should obtain the total price when 'getTotalPrice()' method is called`, inject([CartProductService], (cartProductService: CartProductService) => {
    let added = cartProductService.addProduct(product_code);
    added.then(added => {
      let totalPrice:number = cartProductService.getTotalPrice();
      expect(totalPrice).toBe(20);
    }); 
  }));

});
