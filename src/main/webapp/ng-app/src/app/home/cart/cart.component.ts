import { Component, OnDestroy, NgModule, Input, Output, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { MdDialog, MdSidenav } from '@angular/material';

import { CartProduct } from '../shared/cart-product';

import { ShoppingCartService } from '../shared/shopping-cart.service';
import { TPVService } from '../../shared/tpv.service';
import { ToastService } from '../../shared/toast.service';

@Component({
  selector: 'cart-view',
  templateUrl: './cart.component.html',
  styles: [`
    #paddingContainer {
      padding:10em;
      padding-top:0em;
    }
    #clearCartButton, #openCalculatorButton {
      margin-left:0.4em;
      float:right;
    }
    md-input-container {
      width: 100%;
    }
    md-card {
      margin-bottom:1em;
    }
    .center {
      text-align:center;
    }
  `]
})

export class CartComponent implements OnDestroy {

  @Output() closeSidenavEvent:EventEmitter<boolean>=new EventEmitter();

  codeInput:string = '';
  totalPrice:string = this.shoppingCartService.getTotalPrice().toFixed(2);
  cartProducts: CartProduct[] = this.shoppingCartService.getCartProducts();
  subscription: Subscription;
  columns = [
  { name: 'description' },
  { name: 'retailPrice' },
  { name: 'amount' },
  { name: 'discount' },
  { name: 'delivered' },
  { name: 'totalPrice'}
  ];

  constructor (private shoppingCartService: ShoppingCartService, private toastService: ToastService, private router: Router, public dialog: MdDialog){
    this.subscription = this.shoppingCartService.getCartProductsObservable().subscribe((cartProducts:CartProduct[]) => {
      this.cartProducts = cartProducts;
      this.totalPrice = this.shoppingCartService.getTotalPrice().toFixed(2);
    });
  }

  async onSubmit(cartForm:any, event:Event){ 
  	event.preventDefault();
  	this.shoppingCartService.addProduct(this.codeInput).then(()=>{
     
    }).catch(error =>{
      this.toastService.error('Invalid code', 'The given product code is invalid');
    });
    this.codeInput = '';
  }

  updateProduct(row:any, event:any, attribute:string):void {
    let cartProduct:any = this.cartProducts[row.$$index];
    if (attribute !== 'delivered'){
      cartProduct[attribute] = Number(event.target.value);
    } else {
      cartProduct.delivered = !cartProduct.delivered;
    }
    this.shoppingCartService.updateProduct(cartProduct);
  }

  updateTotalPrice(row:any, event:any):void {
    let cartProduct:any = this.cartProducts[row.$$index];
    let total:number = event.target.value;
    cartProduct.discount = 100*(1-(total/(cartProduct.retailPrice*cartProduct.amount)));
    this.shoppingCartService.updateProduct(cartProduct);
  }

  removeFromCart(cartProduct:CartProduct):void {
    this.shoppingCartService.removeProduct(cartProduct);
  }

  clearCart():void {
  	this.shoppingCartService.clear();
  }

  checkout():void {
    this.router.navigate(['/home/payment']);
    this.closeSidenavEvent.emit(true);
  }

  ngOnDestroy(){
  	this.subscription.unsubscribe();
  }

  openCalculator(){
    this.dialog.open(CalculatorDialog);
  }

}

@Component({
  selector: 'calculator-dialog',
  template: `<calculator-view></calculator-view>`
})
export class CalculatorDialog {}
