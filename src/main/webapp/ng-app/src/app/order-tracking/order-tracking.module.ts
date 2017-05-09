import {NgModule} from '@angular/core';
import {HttpModule} from '@angular/http';
import {CommonModule} from '@angular/common';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import { MaterialModule } from '@angular/material';

import { NgxDatatableModule } from '@swimlane/ngx-datatable';

import {OrderTrackingComponent} from './order-tracking.component';
import {OrderTrackingRoutingModule} from './order-tracking-routing.module';
import {OrderTrackingService} from './order-tracking.service';

import {TPVService} from '../shared/tpv.service';

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
        TPVService, OrderTrackingService
    ],
})
export class OrderTrackingModule {
}