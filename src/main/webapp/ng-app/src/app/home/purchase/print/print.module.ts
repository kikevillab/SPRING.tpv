/**
  * @author Sergio Banegas Cortijo
  * Github: https://github.com/sergiobanegas
*/
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpModule, JsonpModule } from '@angular/http';
import { RouterModule } from '@angular/router';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MaterialModule } from '@angular/material';
import { ToastyModule } from 'ng2-toasty';
import 'hammerjs';

import { PrintComponent } from './print.component';
import { CreateVoucherComponent } from './create-voucher/create-voucher.component';
import { PrintService } from './shared/services/print.service';

import { SharedModule } from '../shared/shared.module';

import { HTTPService } from '../../../shared/services/http.service';
import { ToastService } from '../../../shared/services/toast.service';
import { ShoppingService } from '../../shared/services/shopping.service';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    HttpModule,
    JsonpModule,
    RouterModule,
    BrowserAnimationsModule,
    FlexLayoutModule,
    MaterialModule,
    ToastyModule,
    SharedModule
  ],
  declarations: [ PrintComponent, CreateVoucherComponent ],
  providers: [ PrintService, HTTPService, ToastService, ShoppingService ],
  entryComponents: [ CreateVoucherComponent ]
})
export class PrintModule { }
