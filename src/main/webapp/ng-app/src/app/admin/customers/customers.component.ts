/**
 * Created by fran lopez on 13/05/2017.
 */

import {Component, OnInit} from '@angular/core';
import {CustomersService} from './customers.service';
import {TPVHTTPError} from '../../shared/models/tpv-http-error.model';
import {ToastService} from '../../shared/services/toast.service';
import {User} from '../../shared/models/user.model';
import {MdDialog} from '@angular/material';
import {NewCustomerDialog} from './new-customer/new-customer.component';

@Component({
    templateUrl: './customers.component.html',
    styleUrls: ['./customers.component.css']
})
export class CustomersComponent implements OnInit {
    results = [];

    constructor(private customersService: CustomersService, private toastService: ToastService,
                private newCustomerDialog: MdDialog) {
    }

    ngOnInit(): void {
        this.customersService.findAll().subscribe(
            results => this.results = results.data,
            error => this.handleError(error)
        );
    }

    handleError(httpError: TPVHTTPError) {
        this.toastService.info('ERROR getting results from server', httpError.error);
    }

    loadResultsFound(results: User[]) {
        this.results = results;
    }

    openNewCustomerDialog() {
        let dialogRef = this.newCustomerDialog.open(NewCustomerDialog);
        dialogRef.afterClosed().subscribe(user => {
            this.ngOnInit();
            console.log(user);
        });
    }
}