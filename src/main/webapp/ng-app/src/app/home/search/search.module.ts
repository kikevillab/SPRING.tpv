/**
  * @author Sergio Banegas Cortijo
  * Github: https://github.com/sergiobanegas
*/
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpModule, JsonpModule } from '@angular/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MaterialModule } from '@angular/material';
import 'hammerjs';
import { ToastyModule } from 'ng2-toasty';

import { SharedModule } from '../../shared/shared.module';

import { SearchComponent } from './search.component';
import { ProductDetailsComponent } from './product-details/product-details.component';

import { HTTPService } from '../../shared/services/http.service';
import { LocalStorageService } from '../../shared/services/local-storage.service';
import { ToastService } from '../../shared/services/toast.service';
import { SearchService } from './shared/services/search.service';
import { ShoppingService } from '../shared/services/shopping.service';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    HttpModule,
    JsonpModule,
    BrowserAnimationsModule,
    FlexLayoutModule,
    MaterialModule,
    ToastyModule,
    SharedModule
  ],
  declarations: [ SearchComponent, ProductDetailsComponent ],
  providers: [ HTTPService, LocalStorageService, ToastService, ShoppingService, SearchService ],
  entryComponents: [ ProductDetailsComponent ]
})
export class SearchModule { }
