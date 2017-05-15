import { Component, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';

import { CashierClosure } from '../shared/models/cashier-closure';
import { CashierService } from '../shared/services/cashier.service';
import { ToastService } from '../../shared/services/toast.service';


@Component({
  selector: 'close-cashier-view',
  templateUrl: './close-cashier.component.html',
  styles: [`
  md-card {
    margin-bottom: 1em;
  }
  @media only screen and (min-width: 768px) {
    md-card {
      width:30em;
    }
  }
  `]
})

export class CloseCashierComponent implements OnDestroy {

  countedMoney: number = 0;
  selectedOption: string = 'I agree';
  comment: string = '';
  cashier: CashierClosure;
  cashierSubscription: Subscription;

  constructor(private cashierService: CashierService, private router: Router, private toastService: ToastService){
    this.cashier = this.cashierService.getCurrentCashier();
    this.cashierSubscription = this.cashierService.getCurrentCashierObservable().subscribe((cashier: CashierClosure) => {
      this.cashier = cashier;
    });
  }

  selectOption(entry): void {
    this.selectedOption = entry;
  }

  close(): void {
    let comment: string = this.selectedOption;
    if (this.comment != ''){
      comment+=`. ${this.comment}`;
    }
    this.cashierService.closeCashier(this.countedMoney, comment).then((cashier: CashierClosure) => {
      this.toastService.info('Cashier closed', 'The cashier has been closed');
    }).catch((error: string) => {
      this.toastService.error('Error closing the cashier', error);
    });
  }

  ngOnDestroy(){
    this.cashierSubscription.unsubscribe();
  }

}