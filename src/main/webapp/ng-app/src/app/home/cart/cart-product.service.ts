import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Subject } from 'rxjs/Subject';

import { CartProduct } from './cart-product';
import { Product } from '../product';

import { TPVService } from '../../shared/tpv.service';
import { LocalStorageService } from '../../shared/local-storage.service';

@Injectable()
export class CartProductService {

  private subject:Subject<CartProduct[]> = new Subject<CartProduct[]>();
  private storage_key:string = 'tpv-shopping_cart';
  private cartProducts:CartProduct[] = JSON.parse(this.storageService.getItem(this.storage_key)) || [];
  private totalPrice:number;

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
        resolve(true);
      }, error => resolve(false));
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
    return this.subject.asObservable();
  }

  getTotalPrice():number {
    return this.totalPrice;
  }

  clear():void {
    this.storageService.removeItem(this.storage_key);
    this.cartProducts = [];
    this.updateCart();
  }

  private updateCart():void{
    this.storageService.setItem(this.storage_key, this.cartProducts);
    this.totalPrice = 0.0;
    this.cartProducts.forEach(cartProduct =>{
      this.totalPrice += (cartProduct.amount*cartProduct.retailPrice)-(cartProduct.amount*cartProduct.retailPrice*cartProduct.discount/100);
    });
    this.subject.next(this.cartProducts);
  }

}