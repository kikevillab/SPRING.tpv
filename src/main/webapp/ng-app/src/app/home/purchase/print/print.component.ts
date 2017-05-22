/**
  * @author Sergio Banegas Cortijo
  * Github: https://github.com/sergiobanegas 
*/
import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { MdDialog } from '@angular/material';
import { CreateVoucherComponent } from './create-voucher/create-voucher.component';
import { Subscription } from 'rxjs/Subscription';

import { PrintService } from './shared/services/print.service';
import { ShoppingService } from '../../shared/services/shopping.service';
import { ToastService } from '../../../shared/services/toast.service';

@Component({
  selector: 'print-view',
  templateUrl: 'print.component.html',
  styles: [`
    md-card, button {
      margin-bottom:1em;
    }
    @media only screen and (min-width: 768px) {
      md-card {
        width:20em;
      }
    }
  `]
})
export class PrintComponent implements OnInit, OnDestroy {

  userMobile: number = this.shoppingService.getUserMobile();
  userAssociatedSubscription: Subscription;
  printInvoiceSelected: boolean = false;

  constructor(public shoppingService: ShoppingService, public printService: PrintService, public router: Router, public dialog: MdDialog, private toastService: ToastService) {}

  ngOnInit(){
    this.userAssociatedSubscription = this.shoppingService.getUserMobileObservable().subscribe((userMobile: number) => {
        this.userMobile = userMobile;
    });
    !this.shoppingService.getTicketReference() && this.router.navigate(['/home/purchase/payment']);
  }

  openCreateVoucherDialog(): void {
    this.dialog.open(CreateVoucherComponent);
  }

  printInvoice(): void {
    if (this.userMobile){
      this.printService.createInvoice(this.shoppingService.getTicketReference()).then((invoice: any) => {
      	console.log(invoice);
      }).catch((error: string) => {
      	this.toastService.error('Error generating invoice', error);
      });
    } else {
      this.printInvoiceSelected = true;
    }
  }

  cancelPrintInvoice(): void {
    this.printInvoiceSelected = false;
  }

  ngOnDestroy(){
    this.userAssociatedSubscription && this.userAssociatedSubscription.unsubscribe();
  }

}
