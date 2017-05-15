import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpModule, JsonpModule } from '@angular/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MaterialModule } from '@angular/material';
import 'hammerjs';
import { ToastyModule } from 'ng2-toasty';

import { CloseCashierComponent } from './close-cashier.component';

import { CashierService } from '../shared/services/cashier.service';
import { HTTPService } from '../../shared/services/http.service';
import { ToastService } from '../../shared/services/toast.service';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    HttpModule,
    JsonpModule,
    BrowserAnimationsModule,
    MaterialModule,
    FlexLayoutModule,
    ToastyModule
  ],
  declarations: [ CloseCashierComponent ],
  providers: [ HTTPService, CashierService, ToastService ]
})
export class CloseCashierModule { }
