/**
  * @author Sergio Banegas Cortijo
  * Github: https://github.com/sergiobanegas
*/
import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { MdDialog } from '@angular/material';

import { Voucher } from '../../../shared/models/voucher.model';

import { ShoppingService } from '../../../shared/services/shopping.service';
import { ToastService } from '../../../../shared/services/toast.service';

@Component({
  selector: 'change-view',
  templateUrl: './change.component.html',
  styles: [`
    #resetPaymentButton {
      position:absolute;
      right:0px;
      top:0px;
    }
  `]
})

export class ChangeComponent implements OnInit, OnDestroy {

  totalPrice: number = this.shoppingService.getTotalPrice();
  vouchers: Voucher[] = [];
  change: number;
  totalPaid: number = 0;
  cashMoneyReceived: number = this.shoppingService.getCashMoneyReceived();
  cashMoneyReceivedSubscription: Subscription;
  vouchersSubscription: Subscription;

  constructor (private shoppingService: ShoppingService, private toastService: ToastService, public dialog: MdDialog){
  }

  ngOnInit(){
    this.cashMoneyReceivedSubscription = this.shoppingService.getCashMoneyReceivedObservable().subscribe((cashMoneyReceived: number) => {
      this.cashMoneyReceived = cashMoneyReceived;
      this.updateChange();
    });
    this.vouchersSubscription = this.shoppingService.getVouchersObservable().subscribe((vouchers: Voucher[]) => {
      this.vouchers = vouchers;
      this.updateChange();    
    });
  }

  removeVoucher(voucher: Voucher): void {
    this.shoppingService.removeVoucher(voucher);
  }

  resetPayment(): void {
    this.shoppingService.resetPayment();
  }

  private updateChange(): void {
    this.totalPaid = this.shoppingService.getTotalPaid();
    this.change = (this.cashMoneyReceived > 0 && (this.totalPaid > this.totalPrice)) ?
      this.totalPaid - this.totalPrice : 0;
  }

  ngOnDestroy() {
    this.cashMoneyReceivedSubscription && this.cashMoneyReceivedSubscription.unsubscribe();
    this.vouchersSubscription && this.vouchersSubscription.unsubscribe();
  }

}