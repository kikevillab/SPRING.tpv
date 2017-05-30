/**
  * @author Sergio Banegas Cortijo
  * Github: https://github.com/sergiobanegas
*/
import { Component, OnInit, HostListener, ViewChild, ElementRef } from '@angular/core';
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
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

  categories: Category[];
  categoriesRendered: Category[] = [];
  nameInput: string;
  lastNameInput: string;
  filtered: boolean = false;
  displayNumber: number = 20;
  index: number = 0;
  pxScrolled: number = 0;
  @ViewChild('scrollContainer') scrollContainer: ElementRef;

  constructor (private searchService: SearchService, private shoppingService: ShoppingService, private toastService: ToastService, private dialog: MdDialog){}

  ngOnInit(){
  	this.searchService.getCategoryContent().then((categories: Category[]) => {
  		this.categories = categories;
      if (this.categories.length > this.displayNumber) {
        this.categoriesRendered = this.categories.slice(0, this.displayNumber);
        this.index = this.displayNumber;
      } else {
        this.categoriesRendered = this.categories;
      }      
  	});
  }

  addItems(startIndex: number, endIndex: number): void {
    if (this.categories.length < endIndex){
      endIndex = this.categories.length;
    }
    for (let i: number = startIndex; i < endIndex; i++) {
      this.categoriesRendered.push(this.categories[i]);
    }
  }

  scrollToTop(): void {
    this.scrollContainer.nativeElement.scrollTop = 0;
  }

  getScrollToTopButtonTopPx(): number {
    return this.pxScrolled + 5;
  }

  calculateContainerHeight(): number {
    return window.innerHeight - this.scrollContainer.nativeElement.offsetTop - 16;
  }

  @HostListener('window:scroll', [])
  scrollHandler(event: any): void {
    let tracker: any = event.target;
    let limit: number = tracker.scrollHeight - tracker.clientHeight;
    this.pxScrolled = event.target.scrollTop;
    if (tracker.scrollTop === limit && this.categoriesRendered.length < this.categories.length) {
        let start: number = this.index;
        this.index += this.displayNumber;
        this.addItems(start, this.index);
    }
  }

  search(): void {
    this.nameInput && this.searchService.search(this.nameInput).then((categories: Category[]) => {
      this.categories = categories;      
      this.filtered = true;
      this.lastNameInput = this.nameInput;
      this.nameInput = undefined;
    });
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
