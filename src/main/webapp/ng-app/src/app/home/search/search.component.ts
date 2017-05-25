/**
  * @author Sergio Banegas Cortijo
  * Github: https://github.com/sergiobanegas
*/
import { Component, OnInit } from '@angular/core';
import { MdDialog, MdDialogRef } from '@angular/material';

import { ProductDetailsComponent } from './product-details/product-details.component';

import { Category } from './shared/models/category.model';
import { SearchService } from './shared/services/search.service';
import { ShoppingService } from '../shared/services/shopping.service';
import { CapitalizePipe } from '../../shared/pipes/capitalize.pipe';
import { ToastService } from '../../shared/services/toast.service';

@Component({
  selector: 'search-view',
  templateUrl: './search.component.html',
  styles: [`
  	@media only screen and (min-width: 600px) {
		  .category-container {
  			max-width: 25%; 
  		}    
      .margin-sides {
        margin-right: 5em;
        margin-left: 5em;
        position: relative;
      }
	  }
    @media only screen and (min-width: 960px) {
      form {
        width: 30em;
      }
      md-input-container {
        width: 20em;
      }
      #searchResultsContainer {
        padding-left:2em;
      }
      #searchResultsContainer > a {
        font-size: 1.5em;
      }
      .margin-sides {
        margin-right: 10em;
        margin-left: 10em;
        position: relative;
      }
    }
    .image-container {
      padding: 0.5em;
    }
  	.category-container {
  		padding: 0.5em;
      position: relative;
  	}
    .category-card {
      cursor: pointer;
    }
  	.addToCartButton {
  		position: absolute;
	    right: 0px;
	    top: 0px;
  	}
    #resetSearchButton {
      position: absolute;
      right: 0px;
      top: 0px;
      z-index: 3;
    }
  `]
})

export class SearchComponent implements OnInit {

  categories: Category[];
  nameInput: string;
  lastNameInput: string;
  filtered: boolean = false;

  constructor (private searchService: SearchService, private shoppingService: ShoppingService, private toastService: ToastService, private dialog: MdDialog){}

  ngOnInit(){
  	this.searchService.getCategoryContent().then((categories: Category[]) => {
  		this.categories = categories;
  	});
  }

  search(): void {
    this.filtered = true;
    if (this.nameInput){
      this.lastNameInput = this.nameInput;
    }
  	this.nameInput = undefined;
  }

  resetSearch(): void {
    this.filtered = false;
    this.lastNameInput = undefined;
  }

  addToCart(code: string): void {
    this.shoppingService.addProduct(code).then(() => {   
      this.toastService.info('Product added', 'The product has been added to the shopping cart');
    }).catch((error: string) => {
      this.toastService.error('Error adding product', error);
    });
  }

  openCategory(category: Category): void {
    if (category.code) {
      let dialogRef: MdDialogRef<ProductDetailsComponent> = this.dialog.open(ProductDetailsComponent);
      dialogRef.componentInstance.initialize(category.code);
    } else {
      this.searchService.getCategoryContent(category.id).then((categories: Category[]) => {
        this.categories = categories;
      });
    }
  }

  getCategoryBackgroundColor(code: string): string {
    return code ? '#E1F5FE' : '#FFF9C4';
  }

}
