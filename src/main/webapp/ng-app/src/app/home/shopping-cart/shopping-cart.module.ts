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
import { NgxDatatableModule } from '@swimlane/ngx-datatable';
import { ToastyModule } from 'ng2-toasty';

import { CalculatorComponent } from './calculator/calculator.component';

import { HTTPService } from '../../shared/services/http.service';
import { LocalStorageService } from '../../shared/services/local-storage.service';
import { ToastService } from '../../shared/services/toast.service';
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
    NgxDatatableModule,
    ToastyModule
  ],
  declarations: [ CalculatorComponent ],
  providers: [ HTTPService, LocalStorageService, ToastService, ShoppingService ],
  entryComponents: [ CalculatorComponent ]
})
export class ShoppingCartModule { }
