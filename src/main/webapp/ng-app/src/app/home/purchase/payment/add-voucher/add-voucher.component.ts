/**
  * @author Sergio Banegas Cortijo
  * Github: https://github.com/sergiobanegas 
*/
import { Component, OnInit, OnDestroy } from '@angular/core';
import { MdDialogRef } from '@angular/material';
import { Subscription } from 'rxjs/Subscription';

import { Voucher } from '../../../shared/models/voucher.model';
import { ShoppingService } from '../../../shared/services/shopping.service';
import { ToastService } from '../../../../shared/services/toast.service';

@Component({
  selector: 'add-voucher-view',
  templateUrl: './add-voucher.component.html'
})
export class AddVoucherComponent implements OnInit, OnDestroy {

  voucher: Voucher;
  referenceInput: string;
  vouchers: Voucher[] = this.shoppingService.getVouchers();
  vouchersSubscription: Subscription;

  constructor(private shoppingService: ShoppingService, private toastService: ToastService, public dialogRef: MdDialogRef<AddVoucherComponent>){
  }

  ngOnInit(){
    this.vouchersSubscription = this.shoppingService.getVouchersObservable().subscribe((vouchers: Voucher[]) => {
      this.vouchers = vouchers;
    });
  }

  addVoucher(event: any): void {
    event.preventDefault();
    let found: Voucher[] = this.vouchers.filter((voucher: Voucher) => {
      return voucher.reference == this.referenceInput;
    });
    found.length !== 0 ? 
            this.toastService.error('Error adding the voucher', 'The voucher is already added')
          : this.shoppingService.addVoucher(this.referenceInput).then((voucher: Voucher) => {
            this.toastService.success('Voucher added', 'The voucher has been added to the purchase');
            this.referenceInput = undefined;
          }).catch((error: string) => {
            this.referenceInput = undefined;
            this.toastService.error('Error adding the voucher', error);
          });
    this.dialogRef.close();
  }

  ngOnDestroy(){
    this.vouchersSubscription && this.vouchersSubscription.unsubscribe();
  }

}