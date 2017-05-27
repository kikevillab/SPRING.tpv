/**
  * @author Sergio Banegas Cortijo
  * Github: https://github.com/sergiobanegas 
*/
import { Injectable } from '@angular/core';
import { Headers, ResponseContentType, Http, RequestOptions } from '@angular/http';
import { Observable } from 'rxjs';
import { Subject } from 'rxjs/Subject';
import * as moment from 'moment/moment';

import { API_GENERIC_URI } from '../../../../../app.config';

import { VoucherCreation } from '../../../../shared/models/voucher-creation.model';
import { Voucher } from '../../../../shared/models/voucher.model';

import { Amount } from '../../../../shared/models/amount.model';
import { HTTPService } from '../../../../../shared/services/http.service';
import { TPVHTTPError } from '../../../../../shared/models/tpv-http-error.model';
import { LocalStorageService } from '../../../../../shared/services/local-storage.service';

@Injectable()
export class PrintService {

  constructor (private httpService: HTTPService, private http: Http) {}

  createVoucher(amount: number, validity: number): Promise<any> {
    return new Promise((resolve: Function, reject: Function) => {
      let date: Date = new Date();
      let expirationDate: Date = moment(date).add(validity, 'M').toDate();
      let voucherWrapper: VoucherCreation = new VoucherCreation(amount, expirationDate);
      let headers = new Headers([{'Accept': 'application/pdf'}]);
      this.httpService.borrar(`${API_GENERIC_URI}/vouchers`, voucherWrapper, headers).subscribe((response: any) => {
        resolve(response);
      },(error: TPVHTTPError) => {
        reject(error.description);
      });
    });
  }

  createInvoice(ticketId: number): Promise<any> {
    return new Promise((resolve: Function, reject: Function) => {
        resolve(ticketId);
      // this.httpService.post(`${API_GENERIC_URI}/invoices`, amountWrapper).subscribe((voucher: Voucher) => {
      //   resolve(voucher);
      // },(error: TPVHTTPError) => {
      //   reject(error.description);
      // });
    });
  }

}