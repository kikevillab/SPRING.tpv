/**
  * @author Sergio Banegas Cortijo
  * Github: https://github.com/sergiobanegas
*/
import { Component, OnInit } from '@angular/core';

import { Category } from './category.model';
import { SearchService } from './search.service';

import { CapitalizePipe } from '../../shared/pipes/capitalize.pipe';
import { ToastService } from '../../shared/services/toast.service';

@Component({
  selector: 'search-view',
  templateUrl: './search.component.html',
  styles: [`
  	@media only screen and (min-width: 600px) {
		div > div {
  			max-width: 25%; 
  		}
	}
  	.category-container {
  		padding:0.5em;
  	}
  	md-card {
  		position:relative;
  	}
  	.addToCartButton {
  		position:absolute;
	    right:0px;
	    top:0px;
  	}
  `]
})

export class SearchComponent implements OnInit {

  categories: Category[];
  nameInput: string;

  constructor (private searchService: SearchService, private toastService: ToastService){}

  ngOnInit(){
  	this.searchService.getCategoryContent().then((categories: Category[]) => {
  		this.categories = categories;
  	});
  }

  search(): void {
  	this.nameInput = undefined;
  }

}
