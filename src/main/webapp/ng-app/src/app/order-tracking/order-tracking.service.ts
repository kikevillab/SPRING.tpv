import { Injectable } from '@angular/core';

import { ProductState } from './product-state';
import { HTTPService } from '../shared/http.service';

import { API_GENERIC_URI } from '../app.config';


@Injectable()
export class OrderTrackingService {

  constructor (private httpService: HTTPService) { }

  getTicket(reference:string): Promise<any> {
    return new Promise((resolve,reject) => {
      this.httpService.get(`${API_GENERIC_URI}/tickets/tracking/${reference}`).subscribe((products:ProductState[]) => {
        resolve(products);
      }, error => reject(error));
    });
  }
}