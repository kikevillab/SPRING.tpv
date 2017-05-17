/**
 * Created by fran lopez on 12/05/2017.
 */

import {NgModule} from '@angular/core';
import {DateComponent} from './directives/date.component';
import {CommonModule} from '@angular/common';

@NgModule({
    imports: [CommonModule],
    declarations: [DateComponent],
    exports: [DateComponent]
})
export class SharedModule {
}