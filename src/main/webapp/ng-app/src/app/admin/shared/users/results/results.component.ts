/**
 * Created by fran lopez on 15/05/2017.
 */

import {Component, EventEmitter} from '@angular/core';
import {User} from '../../../../shared/models/user.model';
import {MdDialog, MdDialogConfig} from '@angular/material';
import {TableRecord} from './table-record.model';
import {UserDetailsDialog} from '../details/details.component';

@Component({
    selector: 'results',
    inputs: ['results'],
    outputs: ['onInformationModified'],
    templateUrl: './results.component.html',
    styleUrls: ['./results.component.css']
})
export class ResultsComponent {
    results: User[];
    headers: Object = [{name: 'Mobile'}, {name: 'Username'}, {name: 'DNI'}, {name: 'Email'}];
    dialogConfig: MdDialogConfig;
    onInformationModified: EventEmitter<boolean>;

    constructor(private userDetailsDialog: MdDialog) {
        this.dialogConfig = new MdDialogConfig();
        this.onInformationModified = new EventEmitter();
    }

    onActivate(selection: TableRecord) {
        this.dialogConfig.data = selection.row;
        let dialogRef = this.userDetailsDialog.open(UserDetailsDialog, this.dialogConfig);
        dialogRef.afterClosed().subscribe(informationWasModified => {
            this.onInformationModified.emit(informationWasModified);
        });
    }
}