/**
  * @author Sergio Banegas Cortijo
  * Github: https://github.com/sergiobanegas 
*/
import { Component } from '@angular/core';
import { MdDialogRef } from '@angular/material';

import { Voucher } from '../../../shared/models/voucher.model';
import { PrintService } from '../shared/services/print.service';
import { ToastService } from '../../../../shared/services/toast.service';

@Component({
  selector: 'create-voucher-view',
  templateUrl: 'create-voucher.component.html',
  styles: [`
    button {
      display: block;
      margin: 0 auto;
    }
  `]
})
export class CreateVoucherComponent {

  voucherValueInput: number;

  constructor(public dialogRef: MdDialogRef<CreateVoucherComponent>, private printService: PrintService, private toastService: ToastService) {}

  create(event: Event): void {
    event.preventDefault();
    this.printService.createVoucher(this.voucherValueInput).then((voucher: Voucher) => {
      this.toastService.success('Voucher created', 'The voucher has been created');
    }).catch((error: string) => {
      this.toastService.error('Error creating the voucher', error);
    });
    this.voucherValueInput = undefined;
    this.dialogRef.close();
  }

}
