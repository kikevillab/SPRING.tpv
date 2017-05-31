/**
 * Created by fran lopez on 30/05/2017.
 */

import {Component, OnInit} from '@angular/core';
import {User} from '../../../../shared/models/user.model';
import {MdDialogRef} from '@angular/material';
import {Ticket} from './ticket.model';
import {TPVHTTPError} from '../../../../shared/models/tpv-http-error.model';
import {ToastService} from '../../../../shared/services/toast.service';
import {TicketsService} from './tickets.service';
import {MOBILE_ATTRIBUTE_NAME} from '../../../../shared/models/user.model';

@Component({
    templateUrl: './details.component.html',
    styleUrls: ['./details.component.css']
})
export class UserDetailsDialog implements OnInit {
    user: User;
    tickets: Ticket[];
    headers: Object;

    constructor(private dialogRef: MdDialogRef<UserDetailsDialog>, private httpService: TicketsService,
                private toastService: ToastService) {
        this.user = this.dialogRef._containerInstance.dialogConfig.data;
        this.headers = [{name: 'Reference'}, {name: 'Created'}];
    }

    ngOnInit(): void {
        this.httpService.search(MOBILE_ATTRIBUTE_NAME, this.user.mobile).subscribe(
            results => this.tickets = results.data,
            error => this.handleError(error)
        );
    }

    handleError(httpError: TPVHTTPError) {
        this.toastService.info('ERROR getting tickets from server', httpError.error);
    }
}