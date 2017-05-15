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


import { HomeRoutingModule } from './home-routing.module';

import { HomeComponent, OrderTrackingDialog } from './home.component';
import { CartComponent } from './cart/cart.component';
import { CalculatorComponent } from './cart/calculator/calculator.component'
import { CashierService } from './shared/services/cashier.service';
import { DateComponent } from '../shared/directives/date.component';

import { HTTPService } from '../shared/services/http.service';
import { LocalStorageService } from '../shared/services/local-storage.service';
import { ToastService } from '../shared/services/toast.service';

import { CartModule } from './cart/cart.module';
import { SearchModule } from './search/search.module';
import { PaymentModule } from './payment/payment.module';
import { OpenCashierModule } from './open-cashier/open-cashier.module';
import { CloseCashierModule } from './close-cashier/close-cashier.module';
import { MovementModule } from './movement/movement.module';

@NgModule({
  imports: [
    CommonModule,
    HomeRoutingModule,
    FormsModule,
    HttpModule,
    JsonpModule,
    BrowserAnimationsModule,
    FlexLayoutModule,
    MaterialModule,
    ToastyModule,
    CartModule,
    SearchModule,
    PaymentModule,
    MovementModule,
    OpenCashierModule,
    CloseCashierModule,
    NgxDatatableModule
  ],
  declarations: [
    HomeComponent,
    DateComponent,
    CartComponent,
    OrderTrackingDialog
  ],
  providers: [HTTPService, LocalStorageService, ToastService, CashierService],
  entryComponents: [OrderTrackingDialog]
})
export class HomeModule { }
