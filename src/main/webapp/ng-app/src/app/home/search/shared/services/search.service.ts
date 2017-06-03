/**
  * @author Sergio Banegas Cortijo
  * Github: https://github.com/sergiobanegas 
*/
import { Injectable } from '@angular/core';

import { API_GENERIC_URI, URI_PRODUCTS, URI_CATEGORIES } from '../../../../app.config';

import { Category } from '../models/category.model'; // delete?
import { CategoriesPage } from '../models/categories-page.model'; // delete?

import { Product } from '../../../../shared/models/product.model';
import { HTTPService } from '../../../../shared/services/http.service';
import { TPVHTTPError } from '../../../../shared/models/tpv-http-error.model';

@Injectable()
export class SearchService {
  private parentCategory: number;
  private categories: Category[] = [];
  private static pageSize: number = 20;
  private static CATEGORIES_FULL_URI: string = `${API_GENERIC_URI + URI_CATEGORIES}?size=${SearchService.pageSize}`;

  constructor (private httpService: HTTPService) {
    for (let i = 1; i < 10; i++){
      let number = (i <= 6) ? i : (i%6 +1);
      let image: string = `http://localhost:8080/SPRING.tpv.1.2.0-SNAPSHOT/assets/img/products/img-product-${number}.png`;
      this.categories.push(new Category(i, "category"+i, null, image));
    }
    for (let i = 1; i < 50; i++){
      let number = (i <= 6) ? i : (i%6 +1);
      let image: string = `http://localhost:8080/SPRING.tpv.1.2.0-SNAPSHOT/assets/img/products/img-product-${number}.png`;
      this.categories.push(new Category(i, "product"+i, "840000000111"+i % 8, image));
    }
  }

  getCategoryContent(id?: number): Promise<any> {
    this.parentCategory = id ? id : undefined;
    return new Promise((resolve: Function, reject: Function) => {
      resolve(this.categories);
      // this.httpService.get(`${SearchService.CATEGORIES_FULL_URI}?parent=${this.parentCategory}`).subscribe((categoriesPage: CategoriesPage) => {
      //   resolve(categoriesPage);
      // },(error: TPVHTTPError) => {
      //   reject(error.description);
      // });
    });
  }

  search(name: string): Promise<any> {
    return new Promise((resolve: Function, reject: Function) => {
    resolve(this.categories);
    // this.httpService.get(`${SearchService.CATEGORIES_FULL_URI}?name=${name}`).subscribe((categoriesPage: CategoriesPage) => {
      //   resolve(categoriesPage);
      // },(error: TPVHTTPError) => {
      //   reject(error.description);
      // });
    });
  }

  getProductDetails(code: string): Promise<any> {
    return new Promise((resolve: Function, reject: Function) => {
      this.httpService.get(`${API_GENERIC_URI + URI_PRODUCTS}/${code}`).subscribe((product: Product) => {
        resolve(product);
      },(error: TPVHTTPError) => {
        reject(error.description);
      });
    });
  }

  getParentCategory(): number {
  	return this.parentCategory;
  }

 
}