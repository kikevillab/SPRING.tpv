import {Component, OnDestroy, NgModule} from '@angular/core';
import {Subscription} from 'rxjs/Subscription';
import {MdDialog} from '@angular/material';

import {HTTPService} from '../../shared/services/http.service';
import {ToastService} from '../../shared/services/toast.service';

@Component({
    selector: 'search-view',
    templateUrl: './search.component.html',
    styles: [`

    `]
})

export class SearchComponent {


    constructor(private toastService: ToastService, private httpService: HTTPService) {

    }

}
