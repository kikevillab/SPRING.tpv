import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Subject } from 'rxjs/Subject';

import { CartProduct } from './cart-product';
import { Product } from './product';
import { User } from './user';

import { TPVService } from '../../shared/tpv.service';
import { LocalStorageService } from '../../shared/local-storage.service';

import { TicketCheckout } from '../payment/ticket-checkout';


@Injectable()
export class ShoppingCartService {

  private cartProductsSubject:Subject<CartProduct[]> = new Subject<CartProduct[]>();
  private storage_key:string = 'tpv-shopping_cart';
  private cartProducts:CartProduct[] = JSON.parse(this.storageService.getItem(this.storage_key)) || [];
  private totalPrice:number;
  userMobile:number;

  constructor (private storageService: LocalStorageService, private tpvService: TPVService) {
    this.updateCart();
  }

  addProduct(productCode:string) {
    return new Promise((resolve,reject) => {
      this.tpvService.requestGet(`/products/${productCode}`).subscribe(productDetails => {
        let index:number = this.cartProducts.findIndex(cp => cp.productCode == productCode);
        if (index > -1){
          this.cartProducts[index].amount++;
        } else {
          this.cartProducts.push(new CartProduct(productDetails.code, productDetails.description, productDetails.retailPrice));
        }
        this.updateCart();
        this.storageService.setItem(this.storage_key, this.cartProducts);
        resolve();
      }, error => reject(error));
    });
  }

  updateProduct(cartProduct:CartProduct):void {
    if (Number.isInteger(cartProduct.amount) && cartProduct.amount > 0 && cartProduct.discount >= 0.00 && cartProduct.discount <= 100.00){
      let index:number = this.cartProducts.findIndex(cp => cp.productCode == cartProduct.productCode);
      this.cartProducts[index] = cartProduct;
    } else {
      this.cartProducts = JSON.parse(this.storageService.getItem(this.storage_key)) || [];
    }
    this.updateCart();
  }

  removeProduct(cartProduct:CartProduct):void{
    let index:number = this.cartProducts.indexOf(cartProduct);
    let found = this.cartProducts.filter(cp => {
      return cp.productCode == cartProduct.productCode;
    });
    found && this.cartProducts.splice(index, 1);
    this.updateCart();
  }

  getCartProducts():CartProduct[] {
    return this.cartProducts;
  }

  getCartProductsObservable():Observable<CartProduct[]> {
    return this.cartProductsSubject.asObservable();
  }

  getTotalPrice():number {
    return this.totalPrice;
  }

  clear():void {
    this.storageService.removeItem(this.storage_key);
    this.cartProducts = [];
    this.userMobile = null;
    this.updateCart();
  }

  submitOrder():Promise<any>{
    return new Promise((resolve,reject) => {
      let newTicket = new TicketCheckout(this.cartProducts, this.userMobile);
      this.tpvService.requestPost('/tickets', newTicket).subscribe(ticketCreated => {
        this.clear();
        resolve(ticketCreated);
      }, error => reject(error));
    });
  }

  isShoppingCartEmpty():boolean {
    return this.cartProducts.length == 0;
  }

  associateUser(userMobile:number):Promise<User> {
    return new Promise((resolve,reject) => {
      this.tpvService.requestGet(`/users/${userMobile}`).subscribe((associatedUser:User) => {
        this.userMobile = associatedUser.mobile;
        resolve(associatedUser);
      }, error => reject(error));
    });
  }

  disassociateUser():void {
    this.userMobile = null;
  }

  private updateCart():void{
    this.storageService.setItem(this.storage_key, this.cartProducts);
    this.totalPrice = 0.00;
    this.cartProducts.forEach(cartProduct =>{
      this.totalPrice += (cartProduct.amount*cartProduct.retailPrice)-(cartProduct.amount*cartProduct.retailPrice*cartProduct.discount/100);
    });
    this.cartProductsSubject.next(this.cartProducts);
  }

}