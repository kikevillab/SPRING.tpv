/**
 * Created by fran lopez on 06/06/2017.
 */

import {NgModule} from '@angular/core';
import {HttpModule} from '@angular/http';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {ReactiveFormsModule} from '@angular/forms';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {TicketsComponent} from './tickets.component';
import {FilterComponent} from './filter/filter.component';
import {ResultsComponent} from './results/results.component';
import {TicketDetailsDialog} from './details/details.component';
import {SharedModule} from '../../shared/shared.module';
import {NgxDatatableModule} from '@swimlane/ngx-datatable';
import {AngularMaterialModule} from '../../shared/angular-material.module';
import {HTTPService} from '../../shared/services/http.service';
import {ToastService} from '../../shared/services/toast.service';
import {TicketsService} from '../shared/services/tickets.service';
import {ShoppingsService} from '../shared/services/shoppings.service';
import {InMemoryWebApiModule} from 'angular-in-memory-web-api';
import {InMemoryDataService} from '../../shared/services/in-memory-data.service';

@NgModule({
    imports: [
        CommonModule,
        HttpModule,
        ReactiveFormsModule,
        BrowserAnimationsModule,
        NgxDatatableModule,
        AngularMaterialModule,
        FormsModule,
        SharedModule,
        InMemoryWebApiModule.forRoot(InMemoryDataService, {passThruUnknownUrl: true})
    ],
    declarations: [
        TicketsComponent,
        FilterComponent,
        ResultsComponent,
        TicketDetailsDialog
    ],
    providers: [
        HTTPService,
        ToastService,
        TicketsService,
        ShoppingsService
    ],
    exports: [
        TicketsComponent,
        FilterComponent,
        ResultsComponent,
        TicketDetailsDialog
    ],
    entryComponents: [
        TicketDetailsDialog
    ]
})
export class TicketsModule {
}