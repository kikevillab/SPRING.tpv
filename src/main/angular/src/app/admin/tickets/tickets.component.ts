/**
 * Created by fran lopez on 06/06/2017.
 */

import {Component, OnInit} from '@angular/core';
import {TicketsService} from '../shared/services/tickets.service';
import {TPVHTTPError} from '../../shared/models/tpv-http-error.model';
import {ToastService} from '../../shared/services/toast.service';
import {Ticket} from '../shared/models/ticket.model';
import {TICKETS, TICKETS_URI} from '../admin.config';

@Component({
    templateUrl: './tickets.component.html',
    styleUrls: ['./tickets.component.css']
})
export class TicketsComponent implements OnInit {
    results = [];
    endpoint: string;
    dataType: string;
    selected: Ticket;

    constructor(private httpService: TicketsService, private toastService: ToastService) {
        this.selected = new Ticket();
        this.endpoint = TICKETS_URI;
        this.dataType = TICKETS
    }

    ngOnInit(): void {
        this.httpService.findAll().subscribe(
            results => this.results = results.data,
            error => this.handleError(error)
        );
    }

    handleError(httpError: TPVHTTPError) {
        this.toastService.info('ERROR getting results from server', httpError.error);
    }

    loadResultsFound(results: Ticket[]) {
        this.results = results;
    }
}