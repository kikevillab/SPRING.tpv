import {TestBed, async, inject} from '@angular/core/testing';
import 'hammerjs';

import {Router} from '@angular/router';

import {CartProduct} from './shared/cart-product';

import {CartComponent} from './cart/cart.component';
import {HomeComponent} from './home.component';

import {FormsModule} from '@angular/forms';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

import {MaterialModule} from '@angular/material';
import {FlexLayoutModule} from '@angular/flex-layout';
import {NgxDatatableModule} from '@swimlane/ngx-datatable';
import {SharedModule} from '../shared/modules/shared.module';

import {ToastService} from '../shared/services/toast.service';
import {ShoppingCartService} from './shared/shopping-cart.service';
import {LocalStorageService} from '../shared/services/local-storage.service';
import {HTTPService} from '../shared/services/http.service';
import {ToastyService, ToastyConfig, ToastOptions, ToastData} from 'ng2-toasty';


describe('Component: HomeComponent', () => {

    let fixture, home, element, de;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            imports: [MaterialModule, FlexLayoutModule, NgxDatatableModule, FormsModule, BrowserAnimationsModule, SharedModule],
            declarations: [HomeComponent, CartComponent],
            providers: [
                {provide: Router},
                ToastService,
                ShoppingCartService,
                LocalStorageService,
                HTTPService,
                ToastyService, ToastyConfig, ToastOptions, ToastData
            ]
        });
        fixture = TestBed.createComponent(HomeComponent);
        home = fixture.componentInstance;
        element = fixture.nativeElement;
        de = fixture.debugElement;
    }));

});
