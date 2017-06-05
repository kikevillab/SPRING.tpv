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
import {UserDetailsDialog} from './details/details.component';
import {EditUserDialog} from './edit-user/edit-user.component';
import {SharedModule} from '../../../shared/shared.module';
import {NgxDatatableModule} from '@swimlane/ngx-datatable';
import {AngularMaterialModule} from '../../../shared/angular-material.module';
import {HTTPService} from '../../../shared/services/http.service';
import {ToastService} from '../../../shared/services/toast.service';
import {UsersService} from './users.service';
import {TicketsService} from './details/tickets.service';
import {RegExpFormValidatorService} from '../../../shared/services/reg-exp-form-validator.service';
import {UserForm} from './user-form.service';
import {InMemoryWebApiModule} from 'angular-in-memory-web-api';
import {InMemoryDataService} from '../../../shared/services/in-memory-data.service';
import {BooleanToStringPipe} from './details/bool-to-str.pipe';

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
        NewUserDialog,
        UserDetailsDialog,
        BooleanToStringPipe,
        EditUserDialog
    ],
    providers: [
        HTTPService,
        ToastService,
        UsersService,
        RegExpFormValidatorService,
        TicketsService,
        UserForm
    ],
    exports: [
        UsersComponent,
        FilterComponent,
        ResultsComponent,
        NewUserDialog,
        UserDetailsDialog,
        BooleanToStringPipe,
        EditUserDialog
    ],
    entryComponents: [
        NewUserDialog,
        UserDetailsDialog,
        EditUserDialog
    ]
})
export class UsersModule {
}