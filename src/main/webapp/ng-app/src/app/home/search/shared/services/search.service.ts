/**
  * @author Sergio Banegas Cortijo
  * Github: https://github.com/sergiobanegas 
*/
import { Injectable } from '@angular/core';

import { URI_PRODUCTS, URI_CATEGORIES } from '../../../../app.config';
import { URLSearchParams } from '@angular/http';

import { Category } from '../models/category.model'; // delete?
import { CategoriesPage } from '../models/categories-page.model'; // delete?

import { Product } from '../../../../shared/models/product.model';
import { HTTPService } from '../../../../shared/services/http.service';
import { TPVHTTPError } from '../../../../shared/models/tpv-http-error.model';

@Injectable()
export class SearchService {
  private static pageSize: number = 20;
  private static CATEGORIES_FULL_URI: string = `${URI_CATEGORIES}/search?size=${SearchService.pageSize}`;
  private categoriesRoute: number[] = [];

  constructor (private httpService: HTTPService) {}

  getCategoryContent(id?: number, pageNumber: number = 0): Promise<any> {
    return new Promise((resolve: Function, reject: Function) => {
      id && this.categoriesRoute.indexOf(id) > -1 && this.categoriesRoute.push(id);
      let idString: string = id ? id.toString() : "";
      let params: URLSearchParams = new URLSearchParams();
      params.append('page', pageNumber.toString());
      params.append('id', idString);
      this.httpService.get(`${SearchService.CATEGORIES_FULL_URI}`, null, params).subscribe((categoriesPage: CategoriesPage) => {
        resolve(categoriesPage);
      },(error: TPVHTTPError) => {
        reject(error.description);
      });
    });
  }

  goToPreviousCategory(): Promise<any> {
      return new Promise((resolve: Function, reject: Function) => {
        if (this.categoriesRoute.length > 0) {
          this.categoriesRoute.pop();
          return this.categoriesRoute.length > 0
            ? this.getCategoryContent(this.categoriesRoute[0])
            : this.getCategoryContent();
        }
      });
  }

  search(name: string, pageNumber: number = 0): Promise<any> {
    return new Promise((resolve: Function, reject: Function) => {
      let params: URLSearchParams = new URLSearchParams();
      params.append('page', pageNumber.toString());
      params.append('name', name);
    this.httpService.get(`${SearchService.CATEGORIES_FULL_URI}`).subscribe((categoriesPage: CategoriesPage) => {
        resolve(categoriesPage);
      },(error: TPVHTTPError) => {
        reject(error.description);
      });
    });
  }

  getProductDetails(code: string): Promise<any> {
    return new Promise((resolve: Function, reject: Function) => {
      this.httpService.get(`${URI_PRODUCTS}/${code}`).subscribe((product: Product) => {
        resolve(product);
      },(error: TPVHTTPError) => {
        reject(error.description);
      });
    });
  }

  isRootCategory(): boolean {
    return this.categoriesRoute.length == 0;
  }
 
}