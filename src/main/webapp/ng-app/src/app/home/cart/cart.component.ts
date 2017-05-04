import { Component, OnDestroy, NgModule } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { MdDialog } from '@angular/material';

import { CartProduct } from './cart-product';
import { TicketCheckout } from './ticket-checkout';

import { CartProductService } from './cart-product.service';
import { TPVService } from '../../shared/tpv.service';
import { ToastService } from '../../shared/toast.service';

@Component({
  selector: 'cart-view',
  templateUrl: './cart.component.html',
  styles: [`
    #clearCartButton, #openCalculatorButton {
      margin-left:0.4em;
      float:right;
    }
  `]
})

export class CartComponent implements OnDestroy {

  codeInput:string = '';
  totalPrice:string = this.cartProductService.getTotalPrice().toFixed(2);
  cartProducts: CartProduct[] = this.cartProductService.getCartProducts();
  subscription: Subscription;
  columns = [
  { name: 'description' },
  { name: 'retailPrice' },
  { name: 'amount' },
  { name: 'discount' },
  { name: 'delivered' },
  { name: 'delete' }
  ];

  constructor (private cartProductService: CartProductService, private toastService: ToastService, private tpvService: TPVService, public dialog: MdDialog){
    this.subscription = this.cartProductService.getCartProductsObservable().subscribe((cartProducts:CartProduct[]) => {
      this.cartProducts = cartProducts;
      this.totalPrice = this.cartProductService.getTotalPrice().toFixed(2);
    });
  }

  async onSubmit(cartForm:any, event:Event){ 
  	event.preventDefault();
  	this.cartProductService.addProduct(this.codeInput).then((added:boolean)=>{
      !added && this.toastService.error('Invalid code', 'The given product code is invalid');
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
    this.cartProductService.updateProduct(cartProduct);
  }

  removeFromCart(cartProduct:CartProduct):void {
    this.cartProductService.removeProduct(cartProduct);
  }

  clearCart():void {
  	this.cartProductService.clear();
  }

  checkout():void {
    let newTicket = new TicketCheckout(this.cartProducts);
    this.tpvService.requestPost('/tickets', newTicket).subscribe(ticketCreated => {
      this.toastService.success('Checkout done', `Ticket created with reference ${ticketCreated.ticketReference}`);
      this.clearCart();
    }, error => this.toastService.error('Error in checkout', 'Error creating ticket'));
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
