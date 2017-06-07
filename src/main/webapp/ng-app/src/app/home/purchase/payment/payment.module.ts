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

import { PaymentComponent } from './payment.component';
import { CashPaymentComponent } from './cash-payment/cash-payment.component';
import { VoucherPaymentComponent } from './voucher-payment/voucher-payment.component';
import { ChangeComponent } from './change/change.component';

import { SharedModule } from '../shared/shared.module';

import { HTTPService } from '../../../shared/services/http.service';
import { LocalStorageService } from '../../../shared/services/local-storage.service';
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
  declarations: [ PaymentComponent, CashPaymentComponent, VoucherPaymentComponent, ChangeComponent ],
  providers: [ HTTPService, LocalStorageService, ToastService, ShoppingService ],
  entryComponents: [ CashPaymentComponent, VoucherPaymentComponent ]
})
export class PaymentModule { }
