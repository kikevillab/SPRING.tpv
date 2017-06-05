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
import { NgxDatatableModule } from '@swimlane/ngx-datatable';
import { ToastyModule } from 'ng2-toasty';

import { HomeRoutingModule } from './home-routing.module';
import { HomeGuard } from './home.guard';

import { HomeComponent, OrderTrackingDialog } from './home.component';
import { ShoppingCartComponent } from './shopping-cart/shopping-cart.component';
import { HomeService } from './home.service';
import { CashierService } from './shared/services/cashier.service';
import { AuthService } from './shared/services/auth.service';
import { HTTPService } from '../shared/services/http.service';
import { LocalStorageService } from '../shared/services/local-storage.service';
import { ToastService } from '../shared/services/toast.service';

import { ShoppingCartModule } from './shopping-cart/shopping-cart.module';
import { SearchModule } from './search/search.module';
import { OpenCashierModule } from './open-cashier/open-cashier.module';
import { CloseCashierModule } from './close-cashier/close-cashier.module';
import { MovementModule } from './movement/movement.module';
import { PurchaseModule } from './purchase/purchase.module';
import { TicketsOfTheDayModule } from './tickets-of-the-day/tickets-of-the-day.module';

import { SharedModule } from '../shared/shared.module';

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
    ShoppingCartModule,
    SearchModule,
    PurchaseModule,
    MovementModule,
    OpenCashierModule,
    CloseCashierModule,
    TicketsOfTheDayModule,
    NgxDatatableModule,
    SharedModule
  ],
  declarations: [
    HomeComponent,
    ShoppingCartComponent,
    OrderTrackingDialog
  ],
  providers: [ HomeGuard, HomeService, HTTPService, LocalStorageService, ToastService, CashierService, AuthService ],
  entryComponents: [ OrderTrackingDialog ]
})
export class HomeModule { }
