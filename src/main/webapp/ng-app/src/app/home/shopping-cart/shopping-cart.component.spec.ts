/**
  * @author Sergio Banegas Cortijo
  * Github: https://github.com/sergiobanegas 
*/
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
import { SharedModule } from '../../shared/shared.module';
import { ToastyService, ToastyConfig, ToastOptions, ToastData } from 'ng2-toasty';

import { CartProduct } from '../shared/models/cart-product';
import { ShoppingCartComponent } from './shopping-cart.component';

import { ToastService } from '../../shared/services/toast.service';
import { ShoppingService } from '../shared/services/shopping.service';
import { LocalStorageService } from '../../shared/services/local-storage.service';
import { HTTPService } from '../../shared/services/http.service';


export const MockProduct = {
  code: 12341234,
  reference: 'article6',
  description: 'article6',
  retailPrice: 20.00,
  discontinued: false
}

export const MockTicket = {
  ticketReference: '12341234'
}

describe('Component: ShoppingCartComponent', () => {

  let shoppingCart: ShoppingCartComponent;
  let product_code: number = 12341234;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [ MaterialModule, FlexLayoutModule, NgxDatatableModule, FormsModule, BrowserAnimationsModule, SharedModule ],
      declarations: [ ShoppingCartComponent ],
      providers: [
        { provide: Router },
        ToastService,
        ShoppingService,
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
    let fixture: any = TestBed.createComponent(ShoppingCartComponent);
    shoppingCart = fixture.componentInstance;
    shoppingCart.codeInput = product_code;
  }));

  it(`Should add product to cart when '${product_code}' is submitted`, inject([MockBackend], (mockBackend: MockBackend) => {
    mockBackend.connections.subscribe((conn: MockConnection) => {
      conn.mockRespond(new Response(new ResponseOptions({ body: JSON.stringify(MockProduct) })));
    });
    shoppingCart.onSubmit(new Event('testEvent'));
    let found: boolean = false;
    shoppingCart.cartProducts.forEach((cartProduct: CartProduct) => {
      if (cartProduct.productCode === product_code) found = true;
    });
    expect(found).toBe(true);
    shoppingCart.clearCart();
  }));

  it(`Should remove product with code '${product_code}' of cart`, inject([MockBackend], (mockBackend: MockBackend) => {
    mockBackend.connections.subscribe((conn: MockConnection) => {
      conn.mockRespond(new Response(new ResponseOptions({ body: JSON.stringify(MockProduct) })));
    });
    shoppingCart.onSubmit(new Event('testEvent'));
    shoppingCart.removeFromCart(new CartProduct(product_code, 'DESCRIPTION1', 12.21));
    let found: boolean = false;
    shoppingCart.cartProducts.forEach((cartProduct: CartProduct) => {
      if (cartProduct.productCode === product_code) found = true;
    });
    expect(found).toBe(false);
    shoppingCart.clearCart();
  }));

  it(`Should update the product data when 'updateProduct()' method is called`, inject([MockBackend], (mockBackend: MockBackend) => {
    mockBackend.connections.subscribe((conn: MockConnection) => {
      conn.mockRespond(new Response(new ResponseOptions({ body: JSON.stringify(MockProduct) })));
    });
    shoppingCart.onSubmit(new Event('testEvent'));
    shoppingCart.updateProduct({$$index: 0}, new Event('testEvent'), 'delivered');
    expect(shoppingCart.cartProducts[0].delivered).toBe(false);
  }));

});
