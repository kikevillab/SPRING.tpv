/**
 * Created by fran lopez on 22/05/2017.
 */

import {NgModule} from '@angular/core';
import {HttpModule} from '@angular/http';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {ReactiveFormsModule} from '@angular/forms';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {UsersComponent} from './users.component';
import {FilterComponent} from './filter/filter.component';
import {ResultsComponent} from './results/results.component';
import {NewUserDialog} from './new-user/new-user.component';
import {SharedModule} from '../../../shared/shared.module';
import {NgxDatatableModule} from '@swimlane/ngx-datatable';
import {AngularMaterialModule} from '../../../shared/angular-material.module';
import {HTTPService} from '../../../shared/services/http.service';
import {ToastService} from '../../../shared/services/toast.service';
import {UsersService} from './users.service';
import {RegExpFormValidatorService} from '../../../shared/services/reg-exp-form-validator.service';
import {InMemoryWebApiModule} from 'angular-in-memory-web-api';
import {InMemoryDataService} from '../../../shared/services/in-memory-data.service';

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
        UsersComponent,
        FilterComponent,
        ResultsComponent,
        NewUserDialog
    ],
    providers: [
        HTTPService,
        ToastService,
        UsersService,
        RegExpFormValidatorService
    ],
    exports: [
        UsersComponent,
        FilterComponent,
        ResultsComponent,
        NewUserDialog
    ],
    entryComponents: [
        NewUserDialog
    ]
})
export class UsersModule {
}