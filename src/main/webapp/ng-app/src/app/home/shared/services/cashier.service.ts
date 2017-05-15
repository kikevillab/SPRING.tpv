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
    return new Promise((resolve,reject) => {
      this.httpService.get(`${API_GENERIC_URI}/cashierclosures/last`).subscribe((cashier: CashierClosure) => {
        this.currentCashier = cashier;
        this.currentCashierObservable.next(this.currentCashier);
      },(error: TPVHTTPError) => {
          error.error == '27' ? resolve(new CashierClosure()) : reject(error.description);
      });
    });
  }

  getCurrentCashierObservable(): Observable<CashierClosure> {
    return this.currentCashierObservable.asObservable();
  }

  getCurrentCashier(): CashierClosure {
    return this.currentCashier;
  }

  openCashier(): Promise<any> {
    return new Promise((resolve,reject) => {
       this.httpService.post(`${API_GENERIC_URI}/cashierclosures`).subscribe((cashier: CashierClosure) => {
           this.currentCashier = cashier;
           this.currentCashierObservable.next(this.currentCashier);
           resolve(this.currentCashier);
       },(error: TPVHTTPError) => {
         reject(error.description);
       });
    });
  }

  closeCashier(countedMoney: number, comment: string): Promise<any> {
    return new Promise((resolve,reject) => {
      let closureData: Object = {
        amount: countedMoney,
        comment: comment
      }
      this.httpService.put(`${API_GENERIC_URI}/cashierclosures/close`, closureData).subscribe((cashier: CashierClosure) => {
        this.currentCashier = cashier;
        this.currentCashierObservable.next(this.currentCashier);
        resolve(this.currentCashier);
      },(error: TPVHTTPError) => {
        reject(error.description);
      });
    });
  }

  withdraw(amount: number): Promise<any> {
    return new Promise((resolve,reject) => {
      this.httpService.put(`${API_GENERIC_URI}/cashierclosures/withdraw`, amount).subscribe((cashier: CashierClosure) => {
        this.currentCashier = cashier;
        this.currentCashierObservable.next(this.currentCashier);
        resolve(cashier)
      },(error: TPVHTTPError) => {
        reject(error.description);
      });
    });
  }

  deposit(amount: number): Promise<any> {
    return new Promise((resolve,reject) => {
        this.httpService.put(`${API_GENERIC_URI}/cashierclosures/deposit`, amount).subscribe((cashier: CashierClosure) => {
          this.currentCashier = cashier;
          this.currentCashierObservable.next(this.currentCashier);
          resolve(cashier)
        },(error: TPVHTTPError) => {
          reject(error.description);
      });
    });
  }

}