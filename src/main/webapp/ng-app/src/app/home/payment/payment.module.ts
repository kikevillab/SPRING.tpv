import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpModule, JsonpModule } from '@angular/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import 'hammerjs';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MaterialModule } from '@angular/material';
import { NgxDatatableModule } from '@swimlane/ngx-datatable';
import { ToastyModule } from 'ng2-toasty';

import { PaymentComponent } from './payment.component';
import { CashPaymentComponent, ChangeDialog } from './cash-payment.component';

import { TPVService } from '../../shared/tpv.service';
import { LocalStorageService } from '../../shared/local-storage.service';
import { ToastService } from '../../shared/toast.service';
import { ShoppingCartService } from '../shared/shopping-cart.service';

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
  declarations: [ PaymentComponent, CashPaymentComponent, ChangeDialog ],
  providers: [TPVService, LocalStorageService, ToastService, ShoppingCartService],
  entryComponents: [CashPaymentComponent, ChangeDialog]
})
export class PaymentModule { }
