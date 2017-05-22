/**
 * Created by fran lopez on 15/05/2017.
 */

import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {HttpModule} from '@angular/http';
import {CommonModule} from '@angular/common';
import {ReactiveFormsModule} from '@angular/forms';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {NgxDatatableModule} from '@swimlane/ngx-datatable';
import {CustomersComponent} from './customers.component';
import {FilterComponent} from './filter/filter.component';
import {ResultsComponent} from './results/results.component';
import {NewCustomerDialog} from './new-customer/new-customer.component';
import {HTTPService} from '../../shared/services/http.service';
import {AngularMaterialModule} from '../../shared/angular-material.module';
import {ToastService} from '../../shared/services/toast.service';
import {CustomersService} from './customers.service';
import {RegExpFormValidatorService} from '../../shared/services/reg-exp-form-validator.service';
import {InMemoryWebApiModule} from 'angular-in-memory-web-api';
import {InMemoryDataService} from '../../shared/services/in-memory-data.service';

@NgModule({
    declarations: [
        CustomersComponent,
        FilterComponent,
        ResultsComponent,
        NewCustomerDialog
    ],
    imports: [
        CommonModule,
        FormsModule,
        HttpModule,
        ReactiveFormsModule,
        BrowserAnimationsModule,
        AngularMaterialModule,
        InMemoryWebApiModule.forRoot(InMemoryDataService, {passThruUnknownUrl: true}),
        NgxDatatableModule
    ],
    providers: [
        HTTPService,
        ToastService,
        CustomersService,
        RegExpFormValidatorService
    ],
    entryComponents: [
        NewCustomerDialog
    ]
})
export class CustomersModule {
}