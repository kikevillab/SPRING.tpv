/**
 * Created by fran lopez on 30/05/2017.
 */

import {Component, OnInit} from '@angular/core';
import {User} from '../../../shared/models/user.model';
import {MdDialogRef} from '@angular/material';
import {Ticket} from '../../shared/models/ticket.model';
import {
    Shopping,
    AMOUNT_ATTRIBUTE_NAME,
    DISCOUNT_ATTRIBUTE_NAME,
    DESCRIPTION_ATTRIBUTE_NAME,
    PRICE_ATTRIBUTE_NAME,
    STATE_ATTRIBUTE_NAME,
    CODE_ATTRIBUTE_NAME,
    TICKET_ATTRIBUTE_NAME
} from '../../shared/models/shopping.model';
import {CapitalizePipe} from '../../../shared/pipes/capitalize.pipe';
import {ToastService} from "../../../shared/services/toast.service";
import {UsersService} from "../../shared/services/users.service";
import {USERS_URI} from '../../admin.config';
import {ID_ATTRIBUTE_NAME} from '../../../shared/models/user.model';
import {TPVHTTPError} from "../../../shared/models/tpv-http-error.model";
import {ShoppingsService} from "../../shared/services/shoppings.service";

@Component({
    templateUrl: './details.component.html',
    styleUrls: ['./details.component.css']
})
export class TicketDetailsDialog implements OnInit {
    user: User;
    ticket: Ticket;
    shoppings: Shopping[];
    headers: Object;
    capitalizePipe: CapitalizePipe;

    constructor(private dialogRef: MdDialogRef<TicketDetailsDialog>, private usersService: UsersService,
                private shoppingsService: ShoppingsService, private toastService: ToastService) {
        this.user = new User();
        this.ticket = this.dialogRef._containerInstance.dialogConfig.data;
        this.usersService.setEndpoint(USERS_URI);
        this.capitalizePipe = new CapitalizePipe();
        this.headers = [{name: this.capitalizePipe.transform(CODE_ATTRIBUTE_NAME, false)},
            {name: this.capitalizePipe.transform(AMOUNT_ATTRIBUTE_NAME, false)},
            {name: this.capitalizePipe.transform(DISCOUNT_ATTRIBUTE_NAME, false)},
            {name: this.capitalizePipe.transform(PRICE_ATTRIBUTE_NAME, false)},
            {name: this.capitalizePipe.transform(DESCRIPTION_ATTRIBUTE_NAME, false)},
            {name: this.capitalizePipe.transform(STATE_ATTRIBUTE_NAME, false)}
        ]
    }

    ngOnInit(): void {
        this.usersService.search(ID_ATTRIBUTE_NAME, this.ticket.user).subscribe(
            results => this.user = results.data[0],
            error => this.handleError(error)
        );
        this.shoppingsService.search(TICKET_ATTRIBUTE_NAME, this.ticket.id).subscribe(
            results => this.shoppings = results.data,
            error => this.handleError(error)
        );
    }

    handleError(httpError: TPVHTTPError) {
        this.toastService.info('ERROR getting tickets from server', httpError.error);
    }
}