/**
  * @author Sergio Banegas Cortijo
  * Github: https://github.com/sergiobanegas 
*/
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Subject } from 'rxjs/Subject';

import { API_GENERIC_URI } from '../../../../../app.config';

import { Voucher } from '../../../../shared/models/voucher.model';

import { Amount } from '../../../../shared/models/amount.model';
import { HTTPService } from '../../../../../shared/services/http.service';
import { TPVHTTPError } from '../../../../../shared/models/tpv-http-error.model';
import { LocalStorageService } from '../../../../../shared/services/local-storage.service';


@Injectable()
export class PrintService {

  constructor (private httpService: HTTPService) {}

  createVoucher(amount: number): Promise<any> {
    return new Promise((resolve: Function,reject: Function) => {
      let amountWrapper: Amount = new Amount(amount);
      resolve(new Voucher("1234", 1234, new Date(), null));
      // this.httpService.post(`${API_GENERIC_URI}/vouchers`, amountWrapper).subscribe((voucher: Voucher) => {
      //   resolve(voucher);
      // },(error: TPVHTTPError) => {
      //   reject(error.description);
      // });
    });
  }

  createInvoice(reference: string): Promise<any> {
    return new Promise((resolve: Function,reject: Function) => {
        resolve(reference);
      // this.httpService.post(`${API_GENERIC_URI}/invoices`, amountWrapper).subscribe((voucher: Voucher) => {
      //   resolve(voucher);
      // },(error: TPVHTTPError) => {
      //   reject(error.description);
      // });
    });
  }

}