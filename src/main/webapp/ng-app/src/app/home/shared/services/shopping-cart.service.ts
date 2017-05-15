import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Subject } from 'rxjs/Subject';

import { API_GENERIC_URI } from '../../../app.config';

import { CartProduct } from '../models/cart-product';
import { Product } from '../../../shared/models/product';
import { User } from '../../../shared/models/user';
import { TicketCheckout } from '../models/ticket-checkout';

import { HTTPService } from '../../../shared/services/http.service';
import { TPVHTTPError } from '../../../shared/models/tpv-http-error';
import { LocalStorageService } from '../../../shared/services/local-storage.service';

@Injectable()
export class ShoppingCartService {

  private storage_key: string = 'tpv-shopping_cart';
  private cartProductsSubject: Subject<CartProduct[]> = new Subject<CartProduct[]>();
  private cartProducts: CartProduct[] = JSON.parse(this.storageService.getItem(this.storage_key)) || [];
  private totalPrice: number;
  private userMobile: number;
  private moneyDelivered: number = 0;

  constructor (private storageService: LocalStorageService, private httpService: HTTPService) {
    this.updateCart();
  }

  addProduct(productCode: string): Promise<any> {
    return new Promise((resolve,reject) => {
      this.httpService.get(`${API_GENERIC_URI}/products/${productCode}`).subscribe((productDetails: Product) => {
        let index: number = this.cartProducts.findIndex((cp: CartProduct) => cp.productCode == productCode);
        if (index > -1){
          this.cartProducts[index].amount++;
        } else {
          this.cartProducts.push(new CartProduct(productDetails.code, productDetails.description, productDetails.retailPrice));
        }
        this.updateCart();
        resolve();
      }, (error: TPVHTTPError) => reject(error.description));
    });
  }

  updateProduct(cartProduct: CartProduct): void {
    if (Number.isInteger(cartProduct.amount) && cartProduct.amount > 0 && cartProduct.discount >= 0.00 && cartProduct.discount <= 100.00){
      let index: number = this.cartProducts.findIndex((cp: CartProduct) => cp.productCode == cartProduct.productCode);
      this.cartProducts[index] = cartProduct;
    } else {
      this.cartProducts = JSON.parse(this.storageService.getItem(this.storage_key)) || [];
    }
    this.updateCart();
  }

  removeProduct(cartProduct: CartProduct): void {
    let index: number = this.cartProducts.indexOf(cartProduct);
    let found = this.cartProducts.filter((cp: CartProduct) => {
      return cp.productCode == cartProduct.productCode;
    });
    found && this.cartProducts.splice(index, 1);
    this.updateCart();
  }

  getCartProducts(): CartProduct[] {
    return this.cartProducts;
  }

  getCartProductsObservable(): Observable<CartProduct[]> {
    return this.cartProductsSubject.asObservable();
  }

  getTotalPrice(): number {
    return this.totalPrice;
  }

  getUserMobile(): number {
    return this.userMobile;
  }

  clear(): void {
    this.storageService.removeItem(this.storage_key);
    this.cartProducts = [];
    this.userMobile = null;
    this.moneyDelivered = 0;
    this.updateCart();
  }

  submitOrder(): Promise<any> {
    return new Promise((resolve: Function,reject: Function) => {
      let newTicket = new TicketCheckout(this.cartProducts, this.userMobile);
      this.httpService.post(`${API_GENERIC_URI}/tickets`, newTicket).subscribe((ticketCreated: any) => {
        this.clear();
        resolve(ticketCreated);
      }, (error: TPVHTTPError) => reject(error.description));
    });
  }

  isShoppingCartEmpty(): boolean {
    return this.cartProducts.length == 0;
  }

  associateUser(userMobile: number): Promise<User> {
    return new Promise((resolve: Function,reject: Function) => {
      this.httpService.get(`${API_GENERIC_URI}/users/${userMobile}`).subscribe((associatedUser: User) => {
        this.userMobile = associatedUser.mobile;
        resolve(associatedUser);
      }, (error: TPVHTTPError) => reject(error.description));
    });
  }

  disassociateUser(): void {
    this.userMobile = null;
  }

  setMoneyDelivered(moneyDelivered: number): void {
    this.moneyDelivered = moneyDelivered;
  }

  getMoneyDelivered(): number {
    return this.moneyDelivered;
  }

  private updateCart(): void {
    this.storageService.setItem(this.storage_key, this.cartProducts);
    this.totalPrice = 0.00;
    this.cartProducts.forEach((cartProduct: CartProduct) =>{
      this.totalPrice += (cartProduct.amount * cartProduct.retailPrice) - (cartProduct.amount * cartProduct.retailPrice * cartProduct.discount / 100);
      this.totalPrice = Math.round(this.totalPrice * 100) / 100;
    });
    this.cartProductsSubject.next(this.cartProducts);
  }

}