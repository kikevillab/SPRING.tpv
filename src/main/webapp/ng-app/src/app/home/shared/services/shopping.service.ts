/**
  * @author Sergio Banegas Cortijo
  * Github: https://github.com/sergiobanegas 
*/
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Subject } from 'rxjs/Subject';

import { API_GENERIC_URI } from '../../../app.config';

import { CartProduct } from '../models/cart-product.model';
import { Product } from '../../../shared/models/product.model';
import { User } from '../models/user.model';
import { TicketCheckout } from '../models/ticket-checkout.model';
import { Voucher } from '../models/voucher.model';

import { HTTPService } from '../../../shared/services/http.service';
import { TPVHTTPError } from '../../../shared/models/tpv-http-error.model';
import { LocalStorageService } from '../../../shared/services/local-storage.service';

@Injectable()
export class ShoppingService {

  private storage_key: string = 'tpv-shopping_cart';
  private cartProductsSubject: Subject<CartProduct[]> = new Subject<CartProduct[]>();
  private vouchersSubject: Subject<Voucher[]> = new Subject<Voucher[]>();
  private cashMoneyReceivedSubject: Subject<number> = new Subject<number>();
  private submittedSubject: Subject<boolean> = new Subject<boolean>();
  private userMobileSubject: Subject<number> = new Subject<number>();
  private cartProducts: CartProduct[] = JSON.parse(this.storageService.getItem(this.storage_key)) || [];
  private totalPrice: number;
  private userMobile: number;
  private cashMoneyReceived: number = 0;
  private vouchers: Voucher[] = [];
  private submitted: boolean = false;
  private ticketReference: string;

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
    let found: CartProduct[] = this.cartProducts.filter((cp: CartProduct) => {
      return cp.productCode == cartProduct.productCode;
    });
    found && this.cartProducts.splice(index, 1);
    this.updateCart();
  }

  submitOrder(): Promise<any> {
    return new Promise((resolve: Function, reject: Function) => {
      let newTicket: TicketCheckout = new TicketCheckout(this.cartProducts, this.userMobile);
      this.httpService.post(`${API_GENERIC_URI}/tickets`, newTicket).subscribe((ticketCreated: any) => {
        this.clear();
        this.submitted = true;
        this.submittedSubject.next(this.submitted);
        this.ticketReference = ticketCreated.ticketReference;
        resolve(ticketCreated);
      }, (error: TPVHTTPError) => reject(error.description));
    });
  }

  associateUser(userMobile: number): Promise<User> {
    return new Promise((resolve: Function,reject: Function) => {
      this.httpService.get(`${API_GENERIC_URI}/users/${userMobile}`).subscribe((associatedUser: User) => {
        this.userMobile = associatedUser.mobile;
        this.userMobileSubject.next(this.userMobile);
        resolve(associatedUser);
      }, (error: TPVHTTPError) => reject(error.description));
    });
  }

  disassociateUser(): void {
    this.userMobile = null;
    this.userMobileSubject.next(this.userMobile);
  }

  addVoucher(reference: string): Promise<any> {
    return new Promise((resolve: Function, reject: Function) => {
      this.vouchers.push(new Voucher(reference, 10, new Date(), null));
      this.vouchersSubject.next(this.vouchers);
      resolve();
    });
  }

  removeVoucher(voucher: Voucher): void {
    let index: number = this.vouchers.indexOf(voucher);
    if (index !== -1) {
      this.vouchers.splice(index, 1); 
      this.vouchersSubject.next(this.vouchers);
    }
  } 

  clear(): void {
    this.storageService.removeItem(this.storage_key);
    this.cartProducts = [];
    this.userMobile = null;
    this.resetPayment();
    this.updateCart();
  }

  resetPayment(): void {
    this.cashMoneyReceived = 0.0;
    this.vouchers = [];
    this.cashMoneyReceivedSubject.next(this.cashMoneyReceived);
    this.vouchersSubject.next(this.vouchers);
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

  getCartProducts(): CartProduct[] {
    return this.cartProducts;
  }

  getCartProductsObservable(): Observable<CartProduct[]> {
    return this.cartProductsSubject.asObservable();
  }

  isSubmitted(): boolean {
    return this.submitted;
  }

  getSubmittedObservable(): Observable<boolean> {
    return this.submittedSubject.asObservable();
  }

  getTotalPrice(): number {
    return this.totalPrice;
  }

  getUserMobile(): number {
    return this.userMobile;
  }

  getUserMobileObservable(): Observable<number> {
    return this.userMobileSubject.asObservable();
  }

  getVouchers(): Voucher[] {
    return this.vouchers;
  }

  getVouchersObservable(): Observable<Voucher[]> {
    return this.vouchersSubject.asObservable();
  }

  getCashMoneyReceived(): number {
    return this.cashMoneyReceived;
  }

  getCashMoneyReceivedObservable(): Observable<number> {
    return this.cashMoneyReceivedSubject.asObservable();
  }

  getTicketReference(): string {
    return this.ticketReference;
  }

  isShoppingCartEmpty(): boolean {
    return this.cartProducts.length == 0;
  }

  getTotalPaid(): number {
    let total: number = this.cashMoneyReceived;
    this.vouchers.forEach((voucher: Voucher) => {
      total += voucher.value;
    });
    return total;
  }

  isPaidOut(): boolean {
    return this.getTotalPaid() >= this.totalPrice;
  }

  setCashMoneyReceived(cashAmount: number): void {
    this.cashMoneyReceived = cashAmount;
    this.cashMoneyReceivedSubject.next(this.cashMoneyReceived);
  }

}