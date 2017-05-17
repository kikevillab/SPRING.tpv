import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {HttpModule, JsonpModule} from '@angular/http';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import 'hammerjs';
import {FlexLayoutModule} from '@angular/flex-layout';
import {MaterialModule} from '@angular/material';
import {NgxDatatableModule} from '@swimlane/ngx-datatable';
import {ToastyModule} from 'ng2-toasty';
import {HomeRoutingModule} from './home-routing.module';
import {HomeComponent, OrderTrackingDialog} from './home.component';
import {CartComponent} from './cart/cart.component';
import {HTTPService} from '../shared/services/http.service';
import {LocalStorageService} from '../shared/services/local-storage.service';
import {ToastService} from '../shared/services/toast.service';
import {CartModule} from './cart/cart.module';
import {SearchModule} from './search/search.module';
import {PaymentModule} from './payment/payment.module';
import {SharedModule} from '../shared/shared.module';

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
        NgxDatatableModule,
        SharedModule
    ],
    declarations: [
        HomeComponent,
        CartComponent,
        OrderTrackingDialog
    ],
    providers: [HTTPService, LocalStorageService, ToastService],
    entryComponents: [OrderTrackingDialog]
})
export class HomeModule {
}
