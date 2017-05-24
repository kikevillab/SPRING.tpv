/**
  * @author Sergio Banegas Cortijo
  * Github: https://github.com/sergiobanegas
*/
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MaterialModule } from '@angular/material';

import { UserAssociationComponent } from './directives/user-association.component';


@NgModule({
    imports: [ CommonModule, FormsModule, FlexLayoutModule, MaterialModule ],
    declarations: [ UserAssociationComponent ],
    exports: [ UserAssociationComponent ]
})
export class SharedModule {
}