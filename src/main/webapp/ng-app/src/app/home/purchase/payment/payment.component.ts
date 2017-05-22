/**
  * @author Sergio Banegas Cortijo
  * Github: https://github.com/sergiobanegas
*/
import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { MdDialog, MdDialogRef } from '@angular/material';

import { CartProduct } from '../../shared/models/cart-product.model';
import { Voucher } from '../../shared/models/voucher.model';
import { CashPaymentComponent } from './cash-payment/cash-payment.component';
import { AddVoucherComponent } from './add-voucher/add-voucher.component';

import { ShoppingService } from '../../shared/services/shopping.service';
import { ToastService } from '../../../shared/services/toast.service';

@Component({
  selector: 'payment-view',
  templateUrl: './payment.component.html',
  styles: [`
  md-card {
    margin-bottom:1em;
    margin-right:1em;
    position:relative;
  }
  md-card-content > button {
    margin-bottom:1em;
  }
  @media only screen and (min-width: 768px) {
    #moneyReceivedContainer {
      width:20em;
    }
  }
  `]
})

export class PaymentComponent implements OnInit, OnDestroy{

  totalPrice: number = this.shoppingService.getTotalPrice();
  paidOut: boolean = false;
  shoppingCartSubscription: Subscription;
  cashMoneySubscription: Subscription;
  vouchersSubscription: Subscription;
  submittedSubscription: Subscription;
  vouchers: Voucher[] = [];
  submitted: boolean = this.shoppingService.isSubmitted();

  constructor (private shoppingService: ShoppingService, private toastService: ToastService, public dialog: MdDialog, private router: Router){
  }

  ngOnInit(){
    this.shoppingCartSubscription = this.shoppingService.getCartProductsObservable().subscribe((cartProducts: CartProduct[]) => {
      this.paidOut = false;
      this.shoppingService.resetPayment();
    });
    this.cashMoneySubscription = this.shoppingService.getCashMoneyReceivedObservable().subscribe((moneyReceived: number) => {
        this.paidOut = this.shoppingService.isPaidOut();
    });
    this.vouchersSubscription = this.shoppingService.getVouchersObservable().subscribe((vouchers: Voucher[]) => {
      this.vouchers = vouchers;
      this.paidOut = this.shoppingService.isPaidOut();
    });
    this.submittedSubscription = this.shoppingService.getSubmittedObservable().subscribe((submitted: boolean) => {
        this.submitted = submitted;
        this.router.navigate(['/home/purchase/print']);
    });
    this.isShoppingCartEmpty() && this.router.navigate(['/home']);
    this.submitted && this.router.navigate(['/home/purchase/print']);
  }

  isShoppingCartEmpty(): boolean {
    return this.shoppingService.isShoppingCartEmpty();
  }

  submitOrder(): void {
    this.shoppingService.submitOrder().then((ticketCreated: any) => {
      this.toastService.success('Checkout done', `Ticket created with reference ${ticketCreated.ticketReference}`);
    }).catch((error: string) => {
      this.toastService.error('Error in checkout', error);
    });
  }

  openCashPaymentDialog(): void {
    let dialogRef: MdDialogRef<CashPaymentComponent> = this.dialog.open(CashPaymentComponent);
    dialogRef.afterClosed().subscribe(() => {
      this.paidOut = this.shoppingService.isPaidOut();
    });
  }

  openAddVoucherDialog(): void {
    this.dialog.open(AddVoucherComponent);
  }

  cancel(): void {
    this.shoppingService.clear();
    this.router.navigate(['/home']);
  }

  ngOnDestroy() {
    this.shoppingCartSubscription && this.shoppingCartSubscription.unsubscribe();
    this.cashMoneySubscription && this.cashMoneySubscription.unsubscribe();
    this.vouchersSubscription && this.vouchersSubscription.unsubscribe();
    this.submittedSubscription && this.submittedSubscription.unsubscribe();
  }

}