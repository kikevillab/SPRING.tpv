/**
  * @author Sergio Banegas Cortijo
  * Github: https://github.com/sergiobanegas 
*/
import { Component } from '@angular/core';
import { MdDialogRef } from '@angular/material';

import { ShoppingService } from '../../../shared/services/shopping.service';

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

  totalPrice: number = this.shoppingService.getTotalPrice();
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
  moneyCharged: number;

  constructor(public dialogRef: MdDialogRef<CashPaymentComponent>, private shoppingService: ShoppingService){ }

  addQuantity(quantity: number): void {
    if (this.moneyCharged == undefined){
      this.moneyCharged = 0.0;
    }
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
    this.shoppingService.setCashMoneyReceived(this.moneyCharged);
  }
}