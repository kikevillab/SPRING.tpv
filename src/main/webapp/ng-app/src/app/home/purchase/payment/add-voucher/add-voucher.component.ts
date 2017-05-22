/**
  * @author Sergio Banegas Cortijo
  * Github: https://github.com/sergiobanegas 
*/
import { Component, OnInit, OnDestroy } from '@angular/core';
import { MdDialogRef } from '@angular/material';

import { Voucher } from '../../../shared/models/voucher.model';
import { ShoppingService } from '../../../shared/services/shopping.service';
import { ToastService } from '../../../../shared/services/toast.service';

@Component({
  selector: 'add-voucher-view',
  templateUrl: './add-voucher.component.html',
  styles: [`
  `]
})
export class AddVoucherComponent {

  voucher: Voucher;
  referenceInput: string;

  constructor(private shoppingService: ShoppingService, private toastService: ToastService, public dialogRef: MdDialogRef<AddVoucherComponent>){
  }

  addVoucher(event: any) {
    event.preventDefault();
    this.shoppingService.addVoucher(this.referenceInput).then(() => {
      this.toastService.success('Voucher added', 'The voucher has been added to the purchase');
      this.referenceInput = undefined;
      this.dialogRef.close();
    }).catch((error: string) => {
      this.referenceInput = undefined;
      this.toastService.error('Error adding the voucher', error);
    });
  }

}