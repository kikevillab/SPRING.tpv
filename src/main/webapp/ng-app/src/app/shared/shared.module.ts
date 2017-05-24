/**
  * @author Fran López
  * @author Sergio Banegas Cortijo
  * Github: https://github.com/sergiobanegas 
*/
import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {DateComponent} from './directives/date.component';
import {CapitalizePipe} from './pipes/capitalize.pipe';
@NgModule({
    imports: [CommonModule],
    declarations: [DateComponent, CapitalizePipe],
    exports: [DateComponent, CapitalizePipe]
})
export class SharedModule {
}