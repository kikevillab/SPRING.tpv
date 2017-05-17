import {Component} from '@angular/core';
import {Location} from '@angular/common';
import {Router, ActivatedRoute, Params} from '@angular/router';

import {ProductState} from './product-state';
import {OrderTrackingService} from './order-tracking.service';

import {TPVHTTPError} from '../shared/models/tpv-http-error';

@Component({
    selector: 'order-tracking-view',
    templateUrl: './order-tracking.component.html',
    styles: [`
        md-spinner {
            margin: 0 auto;
        }

        .green {
            color: green;
        }

        #backButton {
            cursor: pointer;
        }

        .red {
            color: red;
        }

        .red > button {
            cursor: auto;
        }
    `]
})
export class OrderTrackingComponent {

    ticketReference: string;
    loading: boolean = true;
    products: ProductState[];
    error: boolean = false;
    columns = [
        {name: 'productCode'},
        {name: 'description'},
        {name: 'shoppingState'}
    ];

    constructor(private route: ActivatedRoute, private orderTrackingService: OrderTrackingService, private location: Location) {
    }

    ngOnInit() {
        this.ticketReference = this.route.snapshot.params['reference'];
        this.orderTrackingService.getTicket(this.ticketReference).then((products: ProductState[]) => {
            this.products = products;
            this.loading = false;
        }).catch((error: TPVHTTPError) => {
            this.error = true;
            this.loading = false;
        });
    }

    goToPreviousPage(): void {
        this.location.back();
    }

}
