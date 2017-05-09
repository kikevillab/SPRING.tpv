import { TestBed, async, inject } from '@angular/core/testing';
import 'hammerjs';

import { Router } from '@angular/router';

import { CartProduct } from './shared/cart-product';

import { DateComponent } from '../shared/date.component';
import { CartComponent } from './cart/cart.component';
import { HomeComponent } from './home.component';

import { FormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { MaterialModule } from '@angular/material';
import { FlexLayoutModule } from '@angular/flex-layout';
import { NgxDatatableModule } from '@swimlane/ngx-datatable';

import { ToastService } from '../shared/toast.service';
import { ShoppingCartService } from './shared/shopping-cart.service';
import { LocalStorageService } from '../shared/local-storage.service';
import { TPVService } from '../shared/tpv.service';
import { ToastyService, ToastyConfig, ToastOptions, ToastData } from 'ng2-toasty';


describe('Component: HomeComponent', () => {

  let fixture, home, element, de;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [MaterialModule, FlexLayoutModule, NgxDatatableModule, FormsModule, BrowserAnimationsModule],
      declarations: [DateComponent, HomeComponent, CartComponent],
      providers: [
      {provide: Router},
      ToastService,
      ShoppingCartService,
      LocalStorageService,
      TPVService,
      ToastyService, ToastyConfig, ToastOptions, ToastData
      ]
    });
    fixture = TestBed.createComponent(HomeComponent);
    home = fixture.componentInstance;
    element = fixture.nativeElement;
    de = fixture.debugElement;
  }));

});
