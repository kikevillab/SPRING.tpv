import { Component } from '@angular/core';
import { Location } from '@angular/common';

import { CashierClosure } from '../shared/models/cashier-closure';
import { CashierService } from '../shared/services/cashier.service';
import { ToastService } from '../../shared/services/toast.service';

@Component({
  selector: 'movement-view',
  templateUrl: './movement.component.html',
  styles: [`
    .margin-bottom {
      margin-bottom:1em;
    }
    @media only screen and (min-width: 768px) {
      md-card {
        width:40em;
      }
      .margin-right {
        margin-right:1em;
      }
    }
  `]
})

export class MovementComponent {

  operation: string = 'Withdraw';
  selectedCommentOption: string;
  otherReasonComment: string = '';
  amount: number = 0.0;
  cashier: CashierClosure;

  constructor(private location: Location, private cashierService: CashierService, private toastService: ToastService){
    this.cashier = cashierService.getCurrentCashier();
    cashierService.getCurrentCashierObservable().subscribe((cashier: CashierClosure) => {
      this.cashier = cashier;
      this.amount = 0;
    });
  }

  selectCommentOption(entry: string): void {
      this.selectedCommentOption = entry;
  }

  validateAmount(): void {
    if (this.amount < 0){
      this.amount = 0;
    } else if (this.amount > this.cashier.amount && this.operation == "Withdraw") {
      this.amount = this.cashier.amount;
    }
  }

  submit():void {
    let fullComment = this.selectedCommentOption;
    if (this.selectedCommentOption === "Other reason"){
      fullComment += `: ${this.otherReasonComment}`;
    }
    if (this.operation == 'Withdraw') {
      this.cashierService.withdraw(this.amount).then((modifiedCashierClosure:CashierClosure) => {
        this.toastService.info('Movement done', 'The withdrawal has been done successfully');
        this.location.back();
      }).catch((error: string) => {
        this.toastService.error('Error making the withdrawal', error);
      });
    } else {
      this.cashierService.deposit(this.amount).then((modifiedCashierClosure:CashierClosure) => {
        this.toastService.info('Movement done', 'The deposit has been done successfully');
        this.location.back();
      }).catch((error: string) => {
        this.toastService.error('Error making the deposit', error);
      });
    }
  }

  isInvalidForm(): boolean {
    return this.selectedCommentOption == null || this.amount == 0 || (this.selectedCommentOption == 'Other reason' && this.otherReasonComment == '')
  }

  getMaxAmount(): number {
    return this.cashier ? this.cashier.amount : 0;
  }

  cancel(): void {
    this.location.back();
  }
}