/**
 * Created by fran lopez on 15/05/2017.
 */

import {Component, EventEmitter} from '@angular/core';
import {User} from '../../../../shared/models/user.model';
import {MdDialog, MdDialogConfig} from '@angular/material';
import {UserDetailsDialog} from '../details/details.component';

@Component({
    selector: 'results',
    inputs: ['results'],
    outputs: ['onSelectedUser', 'onModifiedUser'],
    templateUrl: './results.component.html',
    styleUrls: ['./results.component.css']
})
export class ResultsComponent {
    results: User[];
    headers: Object = [{name: 'Mobile'}, {name: 'Username'}, {name: 'DNI'}, {name: 'Email'}];
    dialogConfig: MdDialogConfig;
    onSelectedUser: EventEmitter<User>;
    onModifiedUser: EventEmitter<User>;

    constructor(private userDetailsDialog: MdDialog) {
        this.dialogConfig = new MdDialogConfig();
        this.onSelectedUser = new EventEmitter();
        this.onModifiedUser = new EventEmitter();
    }

    onActivate(selection: any) {
        this.dialogConfig.data = selection.row;
        this.onSelectedUser.emit(selection.row);

        let dialogRef = this.userDetailsDialog.open(UserDetailsDialog, this.dialogConfig);
        dialogRef.afterClosed().subscribe(user => {
            this.onModifiedUser.emit(user);
        });
    }
}