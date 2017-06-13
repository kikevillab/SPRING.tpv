/**
 * Created by fran lopez on 30/05/2017.
 */

import {Component, OnInit} from '@angular/core';
import {User} from '../../../../shared/models/user.model';
import {MdDialogRef} from '@angular/material';
import {Ticket} from '../../models/ticket.model';
import {TPVHTTPError} from '../../../../shared/models/tpv-http-error.model';
import {ToastService} from '../../../../shared/services/toast.service';
import {TicketsService} from '../../services/tickets.service';
import {USER_ID_ATTRIBUTE_NAME} from '../../models/ticket.model';
import {MdDialog, MdDialogConfig} from '@angular/material';
import {EditUserDialog} from '../edit-user/edit-user.component';
import {isUndefined} from "util";

@Component({
    templateUrl: './details.component.html',
    styleUrls: ['./details.component.css']
})
export class UserDetailsDialog implements OnInit {
    user: User;
    tickets: Ticket[];
    headers: Object;
    editUserDialogConfig: MdDialogConfig;

    constructor(private dialogRef: MdDialogRef<UserDetailsDialog>, private httpService: TicketsService,
                private toastService: ToastService, private editUserDialog: MdDialog) {
        this.editUserDialogConfig = new MdDialogConfig();
        this.user = this.dialogRef._containerInstance.dialogConfig.data;
        this.editUserDialogConfig.data = this.user;
        this.headers = [{name: 'Reference'}, {name: 'Created'}];
    }

    ngOnInit(): void {
        this.httpService.search(USER_ID_ATTRIBUTE_NAME, this.user.id).subscribe(
            results => this.tickets = results.data,
            error => this.handleError(error)
        );
    }

    handleError(httpError: TPVHTTPError) {
        this.toastService.info('ERROR getting tickets from server', httpError.error);
    }

    deleteUser() {
        this.dialogRef.close(null);
    }

    editUser() {
        let dialogRef = this.editUserDialog.open(EditUserDialog, this.editUserDialogConfig);
        dialogRef.afterClosed().subscribe(user => {
            if (!isUndefined(user))
                this.dialogRef.close(new User(user.mobile, user.password, user.dni, user.email, user.username,
                    user.address, user.active, user.id));
        });
    }
}