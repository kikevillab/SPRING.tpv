/**
  * @author Sergio Banegas Cortijo
  * Github: https://github.com/sergiobanegas 
*/
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpModule, JsonpModule } from '@angular/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import 'hammerjs';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MaterialModule } from '@angular/material';
import { ToastyModule } from 'ng2-toasty';

import { OpenCashierComponent } from './open-cashier.component';

import { CashierService } from '../shared/services/cashier.service';
import { HTTPService } from '../../shared/services/http.service';
import { ToastService } from '../../shared/services/toast.service';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    HttpModule,
    JsonpModule,
    BrowserAnimationsModule,
    FlexLayoutModule,
    MaterialModule,
    ToastyModule
  ],
  declarations: [ OpenCashierComponent ],
  providers: [ HTTPService, ToastService, CashierService ]
})
export class OpenCashierModule { }
