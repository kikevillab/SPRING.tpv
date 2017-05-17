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
import { ToastyModule } from 'ng2-toasty';
import 'hammerjs';

import { PaymentComponent } from './payment.component';
import { CashPaymentComponent, ChangeDialog } from './cash-payment/cash-payment.component';

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
  ToastyModule
  ],
  declarations: [ PaymentComponent, CashPaymentComponent, ChangeDialog ],
  providers: [ HTTPService, LocalStorageService, ToastService, ShoppingService ],
  entryComponents: [ CashPaymentComponent, ChangeDialog]
})
export class PaymentModule { }
