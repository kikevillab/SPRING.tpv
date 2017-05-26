/**
  * @author Sergio Banegas Cortijo
  * Github: https://github.com/sergiobanegas 
*/
import { Injectable } from '@angular/core';

import { API_GENERIC_URI } from '../../../../app.config';

import { Category } from '../models/category.model';
import { ProductDetails } from '../models/product-details.model';
import { Article } from '../models/article.model'; // delete

import { Product } from '../../../../shared/models/product.model';
import { HTTPService } from '../../../../shared/services/http.service';
import { TPVHTTPError } from '../../../../shared/models/tpv-http-error.model';

@Injectable()
export class SearchService {

  private parentCategory: number;

  private categories: Category[] = [];

  constructor (private httpService: HTTPService) {
    for (let i = 1; i < 10; i++){
      let number = (i <= 6) ? i : (i%6 +1);
      let image: string = `http://localhost:8080/SPRING.tpv.1.2.0-SNAPSHOT/assets/img/products/img-product-${number}.png`;
      this.categories.push(new Category(i, "category"+i, null, image));
    }
    for (let i = 1; i < 8; i++){
      let number = (i <= 6) ? i : (i%6 +1);
      let image: string = `http://localhost:8080/SPRING.tpv.1.2.0-SNAPSHOT/assets/img/products/img-product-${number}.png`;
      this.categories.push(new Category(i, "article"+i, "8400000111"+i, image));
    }
    for (let i = 1; i < 8; i++){
      let number = (i <= 6) ? i : (i%6 +1);
      let image: string = `http://localhost:8080/SPRING.tpv.1.2.0-SNAPSHOT/assets/img/products/img-product-${number}.png`;
      this.categories.push(new Category(i, "product"+i, "8400000222"+i, image));
    }
    for (let i = 1; i < 8; i++){
      let number = (i <= 6) ? i : (i%6 +1);
      let image: string = `http://localhost:8080/SPRING.tpv.1.2.0-SNAPSHOT/assets/img/products/img-product-${number}.png`;
      this.categories.push(new Category(i, "product"+i, "8400000333"+i, image));
    }
  }

  getCategoryContent(id?: number): Promise<any> {
    this.parentCategory = id ? id : undefined;
    return new Promise((resolve: Function, reject: Function) => {
      resolve(this.categories);
      // this.httpService.get(`${API_GENERIC_URI}/categories?parent=${this.rootCategory}`).subscribe((categories: Category[]) => {
      //   resolve(categories);
      // },(error: TPVHTTPError) => {
      //   reject(error.description);
      // });
    });
  }

  getProductDetails(code: string): Promise<any> {
    return new Promise((resolve: Function, reject: Function) => {
      let productDetails: ProductDetails = new ProductDetails();
      let article: Article = new Article(code, 'reference', 'article1', 1234, false, "http://localhost:8080/SPRING.tpv.1.2.0-SNAPSHOT/assets/img/products/img-product-1.png", 12, 312);
      productDetails.article = article;
      productDetails.type = "Article";
      productDetails.product = new Product(article.code, article.reference, article.description, article.retailPrice, article.discontinued, article.image);
      resolve(productDetails);
      // this.httpService.get(`${API_GENERIC_URI}/products/details/${code}`).subscribe((productDetails: ProductDetails) => {
      //   resolve(productDetails);
      // },(error: TPVHTTPError) => {
      //   reject(error.description);
      // });
    });
  }

 
}