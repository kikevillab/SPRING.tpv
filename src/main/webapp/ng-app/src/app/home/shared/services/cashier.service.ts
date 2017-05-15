import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Subject } from 'rxjs/Subject';

import { API_GENERIC_URI } from '../../../app.config';

import { CashierClosure } from '../models/cashier-closure';

import { HTTPService } from '../../../shared/services/http.service';
import { TPVHTTPError } from '../../../shared/models/tpv-http-error';
import { LocalStorageService } from '../../../shared/services/local-storage.service';


@Injectable()
export class CashierService {

  private currentCashierObservable: Subject<CashierClosure> = new Subject<CashierClosure>();
  private currentCashier: CashierClosure;

  constructor (private httpService: HTTPService) {}

  initialize(): Promise<any> {
    this.currentCashier = new CashierClosure(1, 52, new Date(), new Date(), 'comment');
    return new Promise((resolve,reject) => {
       this.currentCashierObservable.next(this.currentCashier);
       resolve(this.currentCashier);
    });
    // this.httpService.get(`${API_GENERIC_URI}/cashierclosures/last`).subscribe((cashier: CashierClosure) => {
    //   this.currentCashier = cashier;
    //   this.currentCashierObservable.next(this.currentCashier);
    // },(error: TPVHTTPError) => {
    //   reject(error.description);
    // });
  }

  getCurrentCashierObservable(): Observable<CashierClosure> {
    return this.currentCashierObservable.asObservable();
  }

  getCurrentCashier(): CashierClosure {
    return this.currentCashier;
  }

  openCashier(): Promise<any> {
    return new Promise((resolve,reject) => {
       this.currentCashier = new CashierClosure(1, 52, new Date(), null, 'comment');
       this.currentCashierObservable.next(this.currentCashier);
       resolve(this.currentCashier);
       // this.httpService.put(`${API_GENERIC_URI}/cashierclosures/open`).subscribe((cashier: CashierClosure) => {
       //     this.currentCashier = cashier;
       //     this.currentCashierObservable.next(this.currentCashier);
       //     resolve(this.currentCashier);
       // },(error: TPVHTTPError) => {
       //   reject(error.description);
       // });
    });
  }

  closeCashier(countedMoney: number, comment: string): Promise<any> {
    return new Promise((resolve,reject) => {
      this.currentCashier.amount = countedMoney;
      this.currentCashier.closureDate = new Date();
      this.currentCashierObservable.next(this.currentCashier);
      resolve(this.currentCashier);
      // let closureData: Object = {
      //   amount: countedMoney,
      //   comment: comment
      // }
      // this.httpService.put(`${API_GENERIC_URI}/cashierclosures/close`, closureData).subscribe((cashier: CashierClosure) => {
      //   this.currentCashier = cashier;
      //   this.currentCashierObservable.next(this.currentCashier);
      //   resolve(this.currentCashier);
      // },(error: TPVHTTPError) => {
      //   reject(error.description);
      // });
    });
  }

  withdraw(amount: number): Promise<any> {
    this.currentCashier.amount -= amount;
    this.currentCashierObservable.next(this.currentCashier);
    return new Promise((resolve,reject) => {
       resolve(this.currentCashier);
    });
    // return new Promise((resolve,reject) => {
    //   this.httpService.put(`${API_GENERIC_URI}/cashierclosures/withdraw`, amount).subscribe((cashier: CashierClosure) => {
    //     this.currentCashier = cashier;
    //     this.currentCashierObservable.next(this.currentCashier);
    //     resolve(cashier)
    //   },(error: TPVHTTPError) => {
    //     reject(error.description);
    //   });
    // });
  }

  deposit(amount: number): Promise<any> {
    this.currentCashier.amount += amount;
    this.currentCashierObservable.next(this.currentCashier);
    return new Promise((resolve,reject) => {
       resolve(this.currentCashier);
    });
    // return new Promise((resolve,reject) => {
    //     this.httpService.put(`${API_GENERIC_URI}/cashierclosures/deposit`, amount).subscribe((cashier: CashierClosure) => {
    //       this.currentCashier = cashier;
    //       this.currentCashierObservable.next(this.currentCashier);
    //       resolve(cashier)
    //     },(error: TPVHTTPError) => {
    //       reject(error.description);
    //   });
    // });
  }

}