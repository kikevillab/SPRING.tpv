/**
  * @author Sergio Banegas Cortijo
  * Github: https://github.com/sergiobanegas 
*/
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Subject } from 'rxjs/Subject';

import { API_GENERIC_URI } from '../../../app.config';

import { CashierClosure } from '../models/cashier-closure.model';
import { Amount } from '../models/amount.model';
import { CashierClosingData } from '../models/cashier-closing-data.model';

import { HTTPService } from '../../../shared/services/http.service';
import { TPVHTTPError } from '../../../shared/models/tpv-http-error.model';
import { LocalStorageService } from '../../../shared/services/local-storage.service';


@Injectable()
export class CashierService {

  private currentCashierSubject: Subject<CashierClosure> = new Subject<CashierClosure>();
  private currentCashier: CashierClosure;

  constructor (private httpService: HTTPService) {}

  initialize(): void {
      this.httpService.get(`${API_GENERIC_URI}/cashierclosures/last`).subscribe((cashier: CashierClosure) => {
        this.currentCashier = cashier;
        this.currentCashierSubject.next(this.currentCashier);
      },(error: TPVHTTPError) => {
        if (error.error == 'NotExistsCashierClosuresException'){
          this.currentCashier = new CashierClosure();
          this.currentCashierSubject.next(this.currentCashier);
        } else {
          console.log(error.description);        
        }
    });
  }

  getCurrentCashierObservable(): Observable<CashierClosure> {
    return this.currentCashierSubject.asObservable();
  }

  getCurrentCashier(): CashierClosure {
    return this.currentCashier;
  }

  openCashier(): Promise<any> {
    return new Promise((resolve: Function, reject: Function) => {
       this.httpService.post(`${API_GENERIC_URI}/cashierclosures`).subscribe((cashier: CashierClosure) => {
           this.currentCashier = cashier;
           this.currentCashierSubject.next(this.currentCashier);
           resolve(this.currentCashier);
       },(error: TPVHTTPError) => {
         reject(error.description);
       });
    });
  }

  closeCashier(countedMoney: number, comment: string): Promise<any> {
    return new Promise((resolve: Function, reject: Function) => {
      let closureData: CashierClosingData = new CashierClosingData(countedMoney, comment);
      this.httpService.put(`${API_GENERIC_URI}/cashierclosures/close`, closureData).subscribe((cashier: CashierClosure) => {
        this.currentCashier = cashier;
        this.currentCashierSubject.next(this.currentCashier);
        resolve(this.currentCashier);
      },(error: TPVHTTPError) => {
        reject(error.description);
      });
    });
  }

  withdraw(amount: number): Promise<any> {
    return new Promise((resolve: Function, reject: Function) => {
      let amountWrapper: Amount = new Amount(amount);
      this.httpService.put(`${API_GENERIC_URI}/cashierclosures/withdraw`, amountWrapper).subscribe((cashier: CashierClosure) => {
        this.currentCashier = cashier;
        this.currentCashierSubject.next(this.currentCashier);
        resolve(cashier)
      },(error: TPVHTTPError) => {
        reject(error.description);
      });
    });
  }

  deposit(amount: number): Promise<any> {
    let amountWrapper: Amount = new Amount(amount);
    return new Promise((resolve: Function, reject: Function) => {
        this.httpService.put(`${API_GENERIC_URI}/cashierclosures/deposit`, amountWrapper).subscribe((cashier: CashierClosure) => {
          this.currentCashier = cashier;
          this.currentCashierSubject.next(this.currentCashier);
          resolve(cashier)
        },(error: TPVHTTPError) => {
          reject(error.description);
      });
    });
  }

}