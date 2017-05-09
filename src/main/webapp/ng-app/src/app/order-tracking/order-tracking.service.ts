import { Injectable } from '@angular/core';

import { ProductState } from './product-state';
import { TPVService } from '../shared/tpv.service';


@Injectable()
export class OrderTrackingService {

  constructor (private tpvService: TPVService) { }

  getTicket(reference:string): Promise<any> {
    return new Promise((resolve,reject) => {
      this.tpvService.requestGet(`/tickets/tracking/${reference}`).subscribe((products:ProductState[]) => {
        resolve(products);
      }, error => reject(error));
    });
  }
}