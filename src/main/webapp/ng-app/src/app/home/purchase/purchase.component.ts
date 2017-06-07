/**
  * @author Sergio Banegas Cortijo
  * Github: https://github.com/sergiobanegas
*/
import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { MdDialog, MdDialogRef } from '@angular/material';

import { ShoppingService } from '../shared/services/shopping.service';
import { ToastService } from '../../shared/services/toast.service';

@Component({
  selector: 'purchase-view',
  template: `
    <h1 id="page-title">Purchase</h1>
    <router-outlet></router-outlet>
  `
})

export class PurchaseComponent implements OnInit, OnDestroy {


  constructor (private shoppingService: ShoppingService, private toastService: ToastService, public dialog: MdDialog, private router: Router){
  }

  ngOnInit(){
    //(!this.shoppingService.isSubmitted() || this.shoppingService.isShoppingCartEmpty()) && this.router.navigate(['/home/purchase/payment']);
  }

  ngOnDestroy() {
  }

}