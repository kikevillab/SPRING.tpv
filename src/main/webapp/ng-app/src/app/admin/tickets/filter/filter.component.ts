/**
 * Created by fran lopez on 07/06/2017.
 */

import {Component, OnInit, EventEmitter} from '@angular/core';
import {FormGroup, FormBuilder, Validators} from '@angular/forms';
import {MOBILE_ATTRIBUTE_NAME} from '../../../shared/models/user.model';
import {Ticket, REFERENCE_ATTRIBUTE_NAME} from '../../shared/models/ticket.model';
import {Filter, START_DATE_ATTRIBUTE_NAME, END_DATE_ATTRIBUTE_NAME} from './filter.model';
import {TicketsService} from '../../shared/services/tickets.service';
import {ToastService} from '../../../shared/services/toast.service';
import {RegExpFormValidatorService} from '../../../shared/services/reg-exp-form-validator.service';
import {TPVHTTPError} from "../../../shared/models/tpv-http-error.model";
import {isNull} from "util";

@Component({
    selector: 'filter',
    inputs: ['httpService'],
    outputs: ['onTicketsSearched'],
    templateUrl: './filter.component.html',
    styleUrls: ['./filter.component.css']
})
export class FilterComponent implements OnInit {
    filter: Filter;
    httpService: TicketsService;
    filterForm: FormGroup;
    validationMessages = {
        'mobile': {
            'minlength': 'Mobile must be 9 digits long.',
            'maxlength': 'Mobile must be 9 digits long.',
            'invalid': 'Mobile is invalid.'
        },
        'reference': {
            'maxlength': 'Reference cannot be more than 255 characters long.'
        },
        'start_date': {
            'invalid': 'Start date is invalid.'
        },
        'end_date': {
            'invalid': 'Start date is invalid.'
        }
    };
    formErrors = {
        'mobile': '',
        'reference': '',
        'start_date': '',
        'end_date': ''
    };
    onTicketsSearched: EventEmitter<Ticket[]>;

    constructor(private formBuilder: FormBuilder, private formValidatorByRegExp: RegExpFormValidatorService,
                private toastService: ToastService) {
        this.setFilter();
        this.onTicketsSearched = new EventEmitter();
    }

    setFilter() {
        this.filter = new Filter();
    }

    onSubmit(): void {
        let formValues = this.filterForm.value;
        this.filter = new Filter(formValues.mobile, formValues.reference, formValues.start_date, formValues.end_date);
        let fieldName: string = null;
        let fieldValue = null;

        if (!isNull(this.filter.mobile) && (this.filter.mobile.valueOf() > 0)) {
            fieldName = MOBILE_ATTRIBUTE_NAME;
            fieldValue = this.filter.mobile;
        }
        else if (!isNull(this.filter.reference) && (this.filter.reference.length > 0)) {
            fieldName = REFERENCE_ATTRIBUTE_NAME;
            fieldValue = this.filter.reference;
        }
        else if (!isNull(this.filter.start_date) && (this.filter.start_date.length > 0)) {
            fieldName = START_DATE_ATTRIBUTE_NAME;
            fieldValue = this.filter.start_date;
        }
        else if (!isNull(this.filter.end_date) && (this.filter.end_date.length > 0)) {
            fieldName = END_DATE_ATTRIBUTE_NAME;
            fieldValue = this.filter.end_date;
        }

        this.httpService.search(fieldName, fieldValue).subscribe(
            data => {
                this.clearForm();
                this.handleOK(data.data)
            },
            error => {
                this.clearForm();
                this.handleError(error)
            }
        );
    }

    clearForm() {
        this.setFilter();
        this.filterForm.reset();
    }

    handleOK(tickets: Ticket[]) {
        this.onTicketsSearched.emit(tickets);
    }

    handleError(httpError: TPVHTTPError) {
        this.toastService.info('ERROR in "filters"', httpError.error);
    }

    disableSubmit(): boolean {
        return (!(((this.filterForm.controls['mobile'].dirty)
            && (this.formErrors.mobile.length === 0))
            || ((this.filterForm.controls['reference'].dirty)
            && (this.formErrors.reference.length === 0))
            || ((this.filterForm.controls['start_date'].dirty)
            && (this.formErrors.start_date.length === 0))
            || ((this.filterForm.controls['end_date'].dirty)
            && (this.formErrors.end_date.length === 0))
        ));
    }

    ngOnInit(): void {
        this.buildForm();
    }

    buildForm(): void {
        this.filterForm = this.formBuilder.group({
            'mobile': [this.filter.mobile, [Validators.minLength(9), Validators.maxLength(9),
                this.formValidatorByRegExp.regExpFormValidator(/[0-9]{9}/)]],
            'reference': [this.filter.reference, [Validators.maxLength(255)]],
            'start_date': [this.filter.start_date, [
                this.formValidatorByRegExp.regExpFormValidator(/[0-9]{4}-[0-9]{2}-[0-9]{2}/)]],
            'end_date': [this.filter.end_date, [
                this.formValidatorByRegExp.regExpFormValidator(/[0-9]{4}-[0-9]{2}-[0-9]{2}/)]],
        });
        this.filterForm.valueChanges
            .subscribe(data => this.onValueChanged(data));
        this.onValueChanged();
    }

    onValueChanged(data?: any): boolean {
        if (!this.filterForm)
            return;

        let form = this.filterForm;

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

    clearFilters() {
        this.clearForm();
        this.onSubmit();
    }
}