/**
  * @author Sergio Banegas Cortijo
  * Github: https://github.com/sergiobanegas 
*/
import { Injectable } from '@angular/core';
import { Headers } from '@angular/http';
import { Observable } from 'rxjs';
import { Subject } from 'rxjs/Subject';
import * as moment from 'moment/moment';

import { URI_TICKETS, URI_VOUCHERS, URI_INVOICES } from '../../../../../app.config';

import { InvoiceCreation } from '../models/invoice-creation.model';
import { UserMobile } from '../models/user-mobile.model';
import { VoucherCreation } from '../../../../shared/models/voucher-creation.model';
import { Voucher } from '../../../../shared/models/voucher.model';
import { Amount } from '../../../../shared/models/amount.model';
import { ShoppingService } from '../../../../shared/services/shopping.service';
import { HTTPService } from '../../../../../shared/services/http.service';
import { TPVHTTPError } from '../../../../../shared/models/tpv-http-error.model';
import { LocalStorageService } from '../../../../../shared/services/local-storage.service';

@Injectable()
export class PrintService {

  constructor (private httpService: HTTPService, private shoppingService: ShoppingService) {}

  createVoucher(amount: number, validity: number): Promise<any> {
    return new Promise((resolve: Function, reject: Function) => {
      let expirationDate: Date = moment(new Date()).add(validity, 'M').toDate();
      let voucherWrapper: VoucherCreation = new VoucherCreation(amount, expirationDate);
      let headers = new Headers();
      headers.append('Accept', 'application/pdf');
      this.httpService.post(`${URI_VOUCHERS}`, voucherWrapper, headers).subscribe((response: Blob) => {
        resolve(response);
      },(error: TPVHTTPError) => {
        reject(error.description);
      });
    });
  }

  createInvoice(ticketReference: number): Promise<any> {
    return new Promise((resolve: Function, reject: Function) => {
      // let userMobile: number = this.shoppingService.getUserMobile();
      // userMobile
      //   ? this.postInvoice(resolve, reject, tickeReference)
      //   : this.httpService.patch(`${URI_TICKETS}/{ticketId}`, new UserMobile(userMobile)).subscribe((response: any) => {
      //       this.postInvoice(resolve, reject, ticketId);
      //     },(error: TPVHTTPError) => {
      //       reject(error.description);
      //     });
    });
  }

  private postInvoice(resolve: Function, reject: Function, ticketReference: number){
    let invoiceCreationWrapper: InvoiceCreation = new InvoiceCreation(ticketReference);
      let headers = new Headers();
      headers.append('Accept', 'application/pdf');
      this.httpService.post(`${URI_INVOICES}`, invoiceCreationWrapper, headers).subscribe((response: Blob) => {
        resolve(response);
      },(error: TPVHTTPError) => {
        reject(error.description);
      });
  }

}