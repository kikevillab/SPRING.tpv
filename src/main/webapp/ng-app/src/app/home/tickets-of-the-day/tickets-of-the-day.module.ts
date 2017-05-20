/**
  * @author Sergio Banegas Cortijo
  * Github: https://github.com/sergiobanegas
*/
import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { HttpModule } from '@angular/http';
import { RouterModule } from '@angular/router';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from '@angular/material';
import { FlexLayoutModule } from '@angular/flex-layout';

import { NgxDatatableModule } from '@swimlane/ngx-datatable';

import { TicketsOfTheDayComponent } from './tickets-of-the-day.component';
import { TicketsOfTheDayService } from './tickets-of-the-day.service';

import { HTTPService } from '../../shared/services/http.service';

@NgModule({
    declarations: [
        TicketsOfTheDayComponent
    ],
    imports: [
        CommonModule,
        HttpModule,
        MaterialModule,
        FlexLayoutModule,
        BrowserAnimationsModule,
        NgxDatatableModule,
        RouterModule
    ],
    providers: [
        HTTPService, TicketsOfTheDayService
    ],
})
export class TicketsOfTheDayModule {
}