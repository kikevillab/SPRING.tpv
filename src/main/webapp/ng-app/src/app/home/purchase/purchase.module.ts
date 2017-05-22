/**
  * @author Sergio Banegas Cortijo
  * Github: https://github.com/sergiobanegas
*/
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { HttpModule, JsonpModule } from '@angular/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MaterialModule } from '@angular/material';
import { ToastyModule } from 'ng2-toasty';
import 'hammerjs';

import { PaymentModule } from './payment/payment.module';
import { PrintModule } from './print/print.module';

import { PurchaseComponent } from './purchase.component';

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
	  RouterModule,
	  BrowserAnimationsModule,
	  FlexLayoutModule,
	  MaterialModule,
	  ToastyModule,
	  PaymentModule,
	  PrintModule
  ],
  declarations: [ PurchaseComponent ],
  providers: [ HTTPService, LocalStorageService, ToastService, ShoppingService ],
  entryComponents: []
})
export class PurchaseModule { }
