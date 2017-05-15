import { Component } from '@angular/core';
import { MdDialog, MdDialogRef } from '@angular/material';

import { ShoppingCartService } from '../../shared/services/shopping-cart.service';

@Component({
  selector: 'cash-payment-view',
  templateUrl: './cash-payment.component.html',
  styles: [`
  div, md-card {
    margin-bottom:1em;
    position:relative;
  }
  img {
    cursor:pointer;
    width:100%;
  }
  div > div > button {
    position:absolute;
    right:0px;
  }
  `]
})

export class CashPaymentComponent {

  totalPrice: number = this.shoppingCartService.getTotalPrice();
  moneyQuantitiesCharged: Object = {
    "500": 0,
    "200": 0,
    "100": 0,
    "50": 0,
    "20": 0,
    "10": 0,
    "5": 0,
    "2": 0,
    "1": 0,
    "0.5": 0,
    "0.2": 0,
    "0.1": 0,
    "0.05": 0,
    "0.02": 0,
    "0.01": 0
  };
  moneyCharged: number = 0.00;

  constructor(public dialog: MdDialog, public dialogRef: MdDialogRef<CashPaymentComponent>, private shoppingCartService: ShoppingCartService){ }

  addQuantity(quantity: number): void {
    this.moneyQuantitiesCharged[quantity.toString()]++;
    let total = this.moneyCharged + quantity;
    this.moneyCharged=Math.round(total * 100) / 100;
  }

  removeQuantity(quantity: number): void {
    this.moneyQuantitiesCharged[quantity.toString()]--;
    let total = this.moneyCharged - quantity;
    this.moneyCharged = Math.round(total * 100) / 100;
  }

  finishPayment(): void {
    this.dialogRef.close();
    let dialog = this.dialog.open(ChangeDialog);
    dialog.componentInstance.moneyCharged = this.moneyCharged - this.totalPrice;
  }
}

@Component({
  selector: 'change-dialog',
  template: `
    <p>The change is: <b>{{moneyCharged | currency:'EUR':true}}</b></p>
    <button md-raised-button color="primary" (click)="close()">OK</button>
  `, styles: [`
    button {
      display: block;
      margin: 0 auto;
    }
  `]
})
export class ChangeDialog {

  moneyCharged: number = 0;

  constructor(public dialogRef: MdDialogRef<ChangeDialog>) {}

  close(){
    this.moneyCharged = 0;
    this.dialogRef.close();
  }
}