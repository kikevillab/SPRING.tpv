/**
  * @author Sergio Banegas Cortijo
  * Github: https://github.com/sergiobanegas
*/
import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { MdDialog } from '@angular/material';

import { CartProduct } from '../shared/models/cart-product';
import { User } from '../../shared/models/user';
import { CashPaymentComponent } from './cash-payment/cash-payment.component';

import { ShoppingService } from '../shared/services/shopping.service';
import { ToastService } from '../../shared/services/toast.service';

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

export class PaymentComponent implements OnInit, OnDestroy{

  totalPrice: number = this.shoppingService.getTotalPrice();
  paidOut: boolean = false;
  shoppingCartSubscription: Subscription;
  mobileNumberInput: number;
  userAssociated: User;

  constructor (private shoppingService: ShoppingService, private toastService: ToastService, public dialog: MdDialog, private router: Router){
    this.shoppingCartSubscription = this.shoppingService.getCartProductsObservable().subscribe((cartProducts: CartProduct[]) => {
      this.totalPrice = this.shoppingService.getTotalPrice();
      this.paidOut = false;
    });
  }

  ngOnInit(){
    this.shoppingService.isShoppingCartEmpty() && this.router.navigate(['/home']);
  }

  isShoppingCartEmpty(): boolean {
    return this.shoppingService.isShoppingCartEmpty();
  }

  submitOrder(): void {
    this.shoppingService.submitOrder().then((ticketCreated: any) => {
      this.router.navigate(['/home']);
      this.toastService.success('Checkout done', `Ticket created with reference ${ticketCreated.ticketReference}`);
    }).catch((error: string) => {
      this.toastService.error('Error in checkout', error);
    });
  }

  openCashPaymentDialog(){
    let dialogRef = this.dialog.open(CashPaymentComponent);
    dialogRef.afterClosed().subscribe(() => {
      this.paidOut = this.shoppingService.getMoneyDelivered() > this.totalPrice;
    });
  }

  associateUser(event: Event): void {
    event.preventDefault();
    this.shoppingService.associateUser(this.mobileNumberInput).then((userAssociated: User) => {
      this.mobileNumberInput = null;
      this.userAssociated = userAssociated;
      this.toastService.success('Client asociated', `The client with the mobile ${userAssociated.mobile} has been associated`);
    }).catch((error: string) => {
      this.toastService.error('Error associating the client', error);
    });
  }

  disassociateUser(): void {
    this.toastService.info('Client dissasociated', `The client with the mobile ${this.mobileNumberInput} has been disassociated`);
    this.shoppingService.disassociateUser();
    this.userAssociated = null;
  }

  cancel(): void {
    this.shoppingService.clear();
    this.router.navigate(['/home']);
  }

  ngOnDestroy() {
    this.shoppingCartSubscription && this.shoppingCartSubscription.unsubscribe();
  }

}