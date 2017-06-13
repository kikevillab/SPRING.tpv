/**
 * Created by fran lopez on 16/05/2017.
 */

import {Component, OnInit} from '@angular/core';
import {FormGroup} from '@angular/forms';
import {User} from '../../../../shared/models/user.model';
import {TPVHTTPError} from '../../../../shared/models/tpv-http-error.model';
import {MdDialogRef} from '@angular/material';
import {ToastService} from '../../../../shared/services/toast.service';
import {UsersService} from '../../services/users.service';
import {UserForm} from '../../services/user-form.service';

@Component({
    templateUrl: './new-user.component.html',
    styleUrls: ['./new-user.component.css']
})
export class NewUserDialog implements OnInit {
    user: User;
    userForm: FormGroup;

    constructor(private dialogRef: MdDialogRef<NewUserDialog>, private toastService: ToastService,
                private httpService: UsersService, private userFormService: UserForm) {
        this.user = new User();
        this.httpService = this.dialogRef._containerInstance.dialogConfig.data;
        this.userFormService.setUser(this.user);
    }

    onSubmit(): void {
        this.user = this.userFormService.getFormGroup().value;
        this.httpService.create(this.user).subscribe(
            result => this.dialogRef.close(this.user),
            error => this.handleError(error)
        );
    }

    handleError(httpError: TPVHTTPError) {
        this.toastService.info('ERROR in "creation"', httpError.error);
    }

    ngOnInit(): void {
        this.userFormService.buildForm();
        this.userForm = this.userFormService.getFormGroup();
    }
}