/**
 * Created by fran lopez on 16/05/2017.
 */

import {Component, OnInit} from '@angular/core';
import {User} from '../../../../shared/models/user.model';
import {MdDialogRef} from '@angular/material';
import {UserForm} from '../../services/user-form.service';
import {FormGroup} from "@angular/forms";

@Component({
    templateUrl: './edit-user.component.html',
    styleUrls: ['./edit-user.component.css']
})
export class EditUserDialog implements OnInit {
    user: User;
    userForm: FormGroup;

    constructor(private dialogRef: MdDialogRef<EditUserDialog>, private userFormService: UserForm) {
        this.user = this.dialogRef._containerInstance.dialogConfig.data;
        this.userFormService.setUser(this.user);
    }

    onSubmit(): void {
        let formValues = this.userFormService.getFormGroup().value;
        this.dialogRef.close(new User(formValues.mobile, formValues.password, formValues.dni, formValues.email,
            formValues.username, formValues.address, formValues.active, this.user.id));
    }

    ngOnInit(): void {
        this.userFormService.buildForm();
        this.userForm = this.userFormService.getFormGroup();
    }
}