/**
 * Created by fran lopez on 30/05/2017.
 */

import {Component, OnInit} from '@angular/core';
import {User} from '../../../shared/models/user.model';
import {MdDialog, MdDialogConfig, MdDialogRef} from '@angular/material';
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
import {EditShoppingDialog} from '../edit-shopping/edit-shopping.component';
import {isUndefined} from "util";

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
    dialogConfig: MdDialogConfig;
    selected: Shopping;

    constructor(private dialogRef: MdDialogRef<TicketDetailsDialog>, private usersService: UsersService,
                private shoppingsService: ShoppingsService, private toastService: ToastService,
                private editShoppingDialog: MdDialog) {
        this.user = new User();
        this.ticket = this.dialogRef._containerInstance.dialogConfig.data;
        this.usersService.setEndpoint(USERS_URI);
        this.dialogConfig = new MdDialogConfig();
        this.capitalizePipe = new CapitalizePipe();
        this.headers = [{name: this.capitalizePipe.transform(CODE_ATTRIBUTE_NAME, false)},
            {name: this.capitalizePipe.transform(AMOUNT_ATTRIBUTE_NAME, false)},
            {name: this.capitalizePipe.transform(DISCOUNT_ATTRIBUTE_NAME, false)},
            {name: this.capitalizePipe.transform(PRICE_ATTRIBUTE_NAME, false)},
            {name: this.capitalizePipe.transform(DESCRIPTION_ATTRIBUTE_NAME, false)},
            {name: this.capitalizePipe.transform(STATE_ATTRIBUTE_NAME, false)}
        ]
    }

    getShoppings() {
        this.shoppingsService.search(TICKET_ATTRIBUTE_NAME, this.ticket.id).subscribe(
            results => this.shoppings = results.data,
            error => this.handleError(error)
        );
    }

    ngOnInit(): void {
        this.usersService.search(ID_ATTRIBUTE_NAME, this.ticket.user).subscribe(
            results => this.user = results.data[0],
            error => this.handleError(error)
        );
        this.getShoppings();
    }

    onActivate(selection: any) {
        this.selected = selection.row;
        this.dialogConfig.data = new Shopping(selection.row.id, selection.row.amount, selection.row.discount,
            selection.row.description, selection.row.price, selection.row.state, selection.row.code,
            selection.row.ticket);
        let dialogRef = this.editShoppingDialog.open(EditShoppingDialog, this.dialogConfig);

        dialogRef.afterClosed().subscribe(shopping => {
            this.editShopping(shopping);
        });
    }

    handleError(httpError: TPVHTTPError) {
        this.toastService.info('ERROR getting tickets from server', httpError.error);
    }

    closeForm(): void {
        this.dialogRef.close();
    }

    editShopping(shopping: Shopping) {
        if (!isUndefined(shopping) && !shopping.equals(this.selected)) {
            this.shoppingsService.put(shopping).subscribe(
                results => this.getShoppings(),
                error => this.handleError(error)
            );
        }
    }
}