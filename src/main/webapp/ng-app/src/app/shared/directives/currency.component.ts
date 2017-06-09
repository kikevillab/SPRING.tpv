/**
  * @author Sergio Banegas Cortijo
  * Github: https://github.com/sergiobanegas 
*/
import { Component, Input } from '@angular/core'

@Component({
  selector: 'currency',
  template: `{{value | currency:'EUR':true}}`
})
export class CurrencyComponent {

  @Input() value: number;

} 