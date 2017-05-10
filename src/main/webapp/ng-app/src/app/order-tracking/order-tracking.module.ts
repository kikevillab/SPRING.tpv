import {NgModule} from '@angular/core';
import {HttpModule} from '@angular/http';
import {CommonModule} from '@angular/common';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import { MaterialModule } from '@angular/material';

import { NgxDatatableModule } from '@swimlane/ngx-datatable';

import {OrderTrackingComponent} from './order-tracking.component';
import {OrderTrackingRoutingModule} from './order-tracking-routing.module';
import {OrderTrackingService} from './order-tracking.service';

import {HTTPService} from '../shared/http.service';

@NgModule({
    declarations: [
        OrderTrackingComponent
    ],
    imports: [
        CommonModule,
        OrderTrackingRoutingModule,
        HttpModule,
        MaterialModule,
        BrowserAnimationsModule,
        NgxDatatableModule
    ],
    providers: [
        HTTPService, OrderTrackingService
    ],
})
export class OrderTrackingModule {
}