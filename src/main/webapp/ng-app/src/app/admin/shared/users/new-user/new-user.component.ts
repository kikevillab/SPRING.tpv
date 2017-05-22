/**
 * Created by fran lopez on 16/05/2017.
 */

import {Component, OnInit} from '@angular/core';
import {FormGroup, FormBuilder, Validators} from '@angular/forms';
import {User} from '../../../../shared/models/user.model';
import {TPVHTTPError} from '../../../../shared/models/tpv-http-error.model';
import {MdDialogRef} from '@angular/material';
import {RegExpFormValidatorService} from '../../../../shared/services/reg-exp-form-validator.service';
import {ToastService} from '../../../../shared/services/toast.service';
import {UsersService} from '../users.service';

@Component({
    templateUrl: './new-user.component.html',
    styleUrls: ['./new-user.component.css']
})
export class NewUserDialog implements OnInit {
    user: User;
    newUserForm: FormGroup;
    formErrors = {
        'mobile': '',
        'email': '',
        'username': '',
        'password': '',
        'address': '',
        'dni': ''
    };
    validationMessages = {
        'mobile': {
            'minlength': 'Mobile must be 9 digits long.',
            'maxlength': 'Mobile must be 9 digits long.',
            'required': 'Mobile is required.'
        },
        'email': {
            'maxlength': 'Email cannot be more than 255 characters long.',
            'required': 'Email is required.',
            'invalid': 'Email is invalid.'
        },
        'username': {
            'maxlength': 'Username cannot be more than 255 characters long.'
        },
        'password': {
            'maxlength': 'Password cannot be more than 255 characters long.',
            'required': 'Password is required.'
        },
        'address': {
            'maxlength': 'Password cannot be more than 255 characters long.'
        },
        'dni': {
            'minlength': 'DNI must be 9 characters long.',
            'maxlength': 'DNI must be 9 characters long.',
            'invalid': 'DNI is invalid.'
        }
    };

    constructor(private dialogRef: MdDialogRef<NewUserDialog>, private formBuilder: FormBuilder,
                private formValidatorByRegExp: RegExpFormValidatorService, private toastService: ToastService,
                private httpService: UsersService) {
        this.user = new User();
        this.httpService = this.dialogRef._containerInstance.dialogConfig.data;
    }

    onSubmit(): void {
        this.user = this.newUserForm.value;
        this.httpService.create(this.user).subscribe(
            result => this.dialogRef.close(this.user),
            error => this.handleError(error)
        );
    }

    handleError(httpError: TPVHTTPError) {
        this.toastService.info('ERROR in "creation"', httpError.error);
    }

    ngOnInit(): void {
        this.buildForm();
    }

    buildForm(): void {
        this.newUserForm = this.formBuilder.group({
            'mobile': [this.user.mobile, [Validators.required, Validators.minLength(9), Validators.maxLength(9)]],
            'email': [this.user.email, [Validators.required, Validators.maxLength(255),
                this.formValidatorByRegExp.regExpFormValidator(
                    /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/)]],
            'username': [this.user.username, [Validators.maxLength(255)]],
            'password': [this.user.password, [Validators.required, Validators.maxLength(255)]],
            'address': [this.user.address, [Validators.maxLength(255)]],
            'dni': [this.user.dni, [Validators.minLength(9), Validators.maxLength(9),
                this.formValidatorByRegExp.regExpFormValidator(/[0-9]{8}[A-Z]/)]],
            'active': [this.user.active, []],
        });
        this.newUserForm.valueChanges
            .subscribe(data => this.onValueChanged(data));
        this.onValueChanged();
    }

    onValueChanged(data?: any): boolean {
        if (!this.newUserForm)
            return;

        let form = this.newUserForm;

        for (const field in this.formErrors) {
            this.formErrors[field] = '';
            const control = form.get(field);

            if (control && control.dirty && !control.valid) {
                const messages = this.validationMessages[field];

                for (const key in control.errors) {
                    this.formErrors[field] += messages[key] + ' ';
                    this.toastService.info('ERROR in "' + field + '" field', messages[key]);
                }
            }
        }

        return false;
    }
}