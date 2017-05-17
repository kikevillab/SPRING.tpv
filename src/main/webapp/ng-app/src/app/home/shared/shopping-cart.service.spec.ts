import {TestBed, async, inject} from '@angular/core/testing';
import {HttpModule, Response, ResponseOptions, BaseRequestOptions, Http} from '@angular/http';
import {MockBackend, MockConnection} from '@angular/http/testing';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/observable/of';

import {CartProduct} from './cart-product';

import {ShoppingCartService} from './shopping-cart.service';
import {LocalStorageService} from '../../shared/services/local-storage.service';
import {HTTPService} from '../../shared/services/http.service';

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
    public requestGet(url: string) {
        let response = new ResponseOptions({
            body: JSON.stringify(MockProduct)
        });
        return Observable.of(new Response(response));
    }

    public requestPost(url: string, object: Object) {
        let response = new ResponseOptions({
            body: JSON.stringify(MockTicket)
        });
        return Observable.of(new Response(response));
    }
}

describe('Service: ShoppingCartService', () => {

    let product_code: string = 'article0';
    let shoppingCartService: ShoppingCartService;
    beforeEach(async(() => {
        TestBed.configureTestingModule({
            imports: [HttpModule],
            providers: [
                ShoppingCartService,
                LocalStorageService,
                MockBackend,
                BaseRequestOptions,
                HTTPService,
                {
                    provide: Http,
                    useFactory: (backend, options) => new Http(backend, options),
                    deps: [MockBackend, BaseRequestOptions]
                }
            ]
        });
        shoppingCartService = TestBed.get(ShoppingCartService);
    }));

    afterEach(() => {
        shoppingCartService.clear();
    });

    it(`Should add product to cart when 'addProduct()' is called`, inject([MockBackend], (mockBackend: MockBackend) => {
        let found: boolean = false;
        mockBackend.connections.subscribe(conn => {
            conn.mockRespond(new Response(new ResponseOptions({body: JSON.stringify(MockProduct)})));
        });
        shoppingCartService.addProduct(product_code);

        shoppingCartService.getCartProducts().forEach(cartProduct => {
            if (cartProduct.productCode == product_code) found = true;
        });
        expect(found).toBe(true);
    }));

    it(`Should remove product with code '${product_code}' of cart`, () => {
        shoppingCartService.addProduct(product_code);
        shoppingCartService.removeProduct(new CartProduct(product_code, 'DESCRIPTION1', 12.21));
        let found: boolean = false;
        shoppingCartService.getCartProducts().forEach(cartProduct => {
            if (cartProduct.productCode === product_code) found = true;
        });
        expect(found).toBe(false);
    });

    it(`Should update the product data when 'updateProduct()' method is called`, () => {
        let added = shoppingCartService.addProduct(product_code);
        added.then(added => {
            let cartProduct: CartProduct = new CartProduct(product_code, 'DESCRIPTION1', 12.21);
            cartProduct.delivered = false;
            shoppingCartService.updateProduct(cartProduct);
            expect(shoppingCartService.getCartProducts()[0].delivered).toBe(false);
        });
    });

    it(`Should clear the cart when 'clear()' method is called`, () => {
        shoppingCartService.addProduct(product_code);
        shoppingCartService.clear();
        expect(shoppingCartService.getCartProducts().length).toBe(0);
    });

    it(`Should obtain the total price when 'getTotalPrice()' method is called`, () => {
        let added = shoppingCartService.addProduct(product_code);
        added.then(added => {
            let totalPrice: number = shoppingCartService.getTotalPrice();
            expect(totalPrice).toBe(20);
        });
    });

    it(`Should associate an user when 'associateUser()' method is called`, () => {
        shoppingCartService.associateUser(666000002).then(userAssociated => {
            expect(userAssociated.mobile).toBe(666000002);
        });
    });

    it(`Should disassociate an user when 'disassociateUser()' method is called`, () => {
        shoppingCartService.disassociateUser();
        expect(shoppingCartService.getUserMobile()).toBeNull();
    });

});
