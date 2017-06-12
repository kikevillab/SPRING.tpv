/**
 * Created by fran lopez on 15/05/2017.
 */

import {Component} from '@angular/core';
import {Ticket} from '../../shared/models/ticket.model';

@Component({
    selector: 'results',
    inputs: ['results'],
    templateUrl: './results.component.html',
    styleUrls: ['./results.component.css']
})
export class ResultsComponent {
    results: Ticket[];
    headers: Object = [{name: 'Reference'}, {name: 'Created'}, {name: 'Mobile'}];
}