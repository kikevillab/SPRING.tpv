/**
 * Created by fran lopez on 02/05/2017.
 */

import {Component, OnInit} from '@angular/core';
import {FormGroup, FormBuilder, Validators} from '@angular/forms';
import {LoginService} from './login.service';
import {TokenService} from './token.service';
import {User} from './user.model';

@Component({
    selector: 'login',
    templateUrl: './login.component.html',
    styleUrls: ['../shared.css']
})
export class LoginComponent implements OnInit {
    user: User;
    loginForm: FormGroup;
    formErrors = {
        'mobile': '',
        'password': '',
        'login': ''
    };
    validationMessages = {
        'mobile': {
            'required': 'Mobile is required.',
            'minlength': 'Mobile must be 9 digits long.',
            'maxlength': 'Mobile must be 9 digits long.',
            'invalid': 'Mobile prefix must be 9 digits long and first '
            + 'must be 6'
        },
        'password': {
            'required': 'Password is required.'
        }
    };

    constructor(private formBuilder: FormBuilder,
                private loginService: LoginService,
                private tokenService: TokenService) {
        this.user = new User(null, '');
    }

    onSubmit(): void {
        this.user = this.loginForm.value;
        this.loginService.login(this.user.mobile, this.user.password)
            .subscribe(
                token => this.tokenService.login(token.token),
                error => this.formErrors.login = error);
    }

    ngOnInit(): void {
        this.buildForm();
    }

    buildForm(): void {
        this.loginForm = this.formBuilder.group({
            'mobile': [this.user.mobile,
                [Validators.required, Validators.minLength(9),
                    Validators.maxLength(9)]
            ],
            'password': [this.user.password, Validators.required]
        });
        this.loginForm.valueChanges
            .subscribe(data => this.onValueChanged(data));
        this.onValueChanged();
    }

    onValueChanged(data?: any): boolean {
        if (!this.loginForm)
            return;

        const form = this.loginForm;

        for (const field in this.formErrors) {
            this.formErrors[field] = '';
            const control = form.get(field);

            if (control && control.dirty && !control.valid) {
                const messages = this.validationMessages[field];

                for (const key in control.errors)
                    this.formErrors[field] += messages[key] + ' ';
            }
        }

        return false;
    }
}