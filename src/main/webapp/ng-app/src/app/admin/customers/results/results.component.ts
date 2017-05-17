/**
 * Created by fran lopez on 15/05/2017.
 */

import {Component} from '@angular/core';
import {User} from '../../../shared/models/user.model';

@Component({
    selector: 'results',
    inputs: ['results'],
    templateUrl: './results.component.html',
    styleUrls: ['./results.component.css']
})
export class ResultsComponent {
    results: User[];
    headers = [{name: 'Mobile'}, {name: 'Username'}, {name: 'DNI'}, {name: 'Email'}];
}