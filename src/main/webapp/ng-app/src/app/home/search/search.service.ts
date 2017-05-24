/**
  * @author Sergio Banegas Cortijo
  * Github: https://github.com/sergiobanegas 
*/
import { Injectable } from '@angular/core';

import { API_GENERIC_URI } from '../../app.config';

import { Category } from './category.model';

import { Product } from '../../shared/models/product.model';
import { HTTPService } from '../../shared/services/http.service';
import { TPVHTTPError } from '../../shared/models/tpv-http-error.model';

@Injectable()
export class SearchService {

  private rootCategory: string;

  private categories: Category[] = [];

  constructor (private httpService: HTTPService) {
    for (let i = 1; i < 20; i++){
      let number = (i <= 6) ? i : (i%6 +1);
      let image: string = `http://localhost:8080/SPRING.tpv.1.2.0-SNAPSHOT/assets/img/products/img-product-${number}.png`;
      this.categories.push(new Category(i, "product"+i, "code"+i, image));
    }
  }

  getCategoryContent(): Promise<any> {
    return new Promise((resolve: Function, reject: Function) => {
      resolve(this.categories);
      // this.httpService.get(`${API_GENERIC_URI}/categories?parent=${this.rootCategory}`).subscribe((categories: Category[]) => {
      //   resolve(categories);
      // },(error: TPVHTTPError) => {
      //   reject(error.description);
      // });
    });
  }

  getProduct(reference: string): Promise<any> {
    return new Promise((resolve: Function, reject: Function) => {
      // this.httpService.get(`${API_GENERIC_URI}/products/${reference}`).subscribe((product: Product) => {
      //   resolve(product);
      // },(error: TPVHTTPError) => {
      //   reject(error.description);
      // });
    });
  }

  setRootCategory(rootCategory: string): void {
    this.rootCategory = rootCategory;
  }

}