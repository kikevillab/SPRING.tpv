import { Component, OnDestroy, NgModule } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { MdDialog } from '@angular/material';

import { CartProduct } from '../shared/cart-product';
import { User } from '../shared/user';
import { TicketCheckout } from './ticket-checkout';
import { CashPaymentComponent } from './cash-payment.component';

import { ShoppingCartService } from '../shared/shopping-cart.service';
import { ToastService } from '../../shared/toast.service';

@Component({
  selector: 'payment-view',
  templateUrl: './payment.component.html',
  styles: [`
  md-card {
    margin-bottom:1em;
    margin-right:1em;
    position:relative;
  }
  #disassociateUser {
    position:absolute;
    right:0px;
    top:0px;
  }
  md-card-content > button {
    margin-bottom:1em;
  }
  md-input-container {
    width:100%;
  }
  `]
})

export class PaymentComponent {

  totalPrice:number = this.shoppingCartService.getTotalPrice();
  paidOut:boolean = false;
  subscription: Subscription;
  mobileNumberInput:number;
  userAssociated: User;
  
  constructor (private shoppingCartService: ShoppingCartService, private toastService: ToastService, public dialog: MdDialog, private router:Router){
    this.subscription = this.shoppingCartService.getCartProductsObservable().subscribe((cartProducts:CartProduct[]) => {
      this.totalPrice = Number(this.shoppingCartService.getTotalPrice().toFixed(2));
    });
  }

  ngOnInit(){
    this.shoppingCartService.isShoppingCartEmpty() && this.router.navigate(['/home']);
  }

  isShoppingCartEmpty():boolean{
    return this.shoppingCartService.isShoppingCartEmpty();
  }

  submitOrder():void {
    this.shoppingCartService.submitOrder().then(ticketCreated =>{
      this.router.navigate(['/home']);
      this.toastService.success('Checkout done', `Ticket created with reference ${ticketCreated.ticketReference}`);
    }).catch(error =>{
      this.toastService.error('Error in checkout', 'Error creating ticket');
    });
  }

  openCashPaymentDialog(){
    let dialogRef = this.dialog.open(CashPaymentComponent);
    dialogRef.afterClosed().subscribe(result => {
      if (dialogRef.componentInstance.moneyCharged>this.totalPrice){
        this.paidOut = true;
      }
    });
  }

  associateUser(event:Event):void {
    event.preventDefault();
    this.shoppingCartService.associateUser(this.mobileNumberInput).then(userAssociated =>{
      this.mobileNumberInput = null;
      this.userAssociated = userAssociated;
      this.toastService.success('Client asociated', `The client with the mobile ${userAssociated.mobile} has been associated`);
    }).catch(error =>{
      this.toastService.error('Error', 'Error associating the client');
    });
  }

  disassociateUser():void {
    this.toastService.info('Client dissasociated', `The client with the mobile ${this.mobileNumberInput} has been disassociated`);
    this.shoppingCartService.disassociateUser();
    this.userAssociated = null;
  }

  cancel():void {
    this.shoppingCartService.clear();
    this.router.navigate(['/home']);
  }

}