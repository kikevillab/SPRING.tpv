/**
  * @author Sergio Banegas Cortijo
  * Github: https://github.com/sergiobanegas
*/
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MaterialModule } from '@angular/material';

import { UserAssociationComponent } from './directives/user-association/user-association.component';
import { UserService } from './directives/user-association/user.service';

@NgModule({
    imports: [ CommonModule, FormsModule, FlexLayoutModule, MaterialModule ],
    declarations: [ UserAssociationComponent ],
    providers: [ UserService ],
    exports: [ UserAssociationComponent ]
})
export class SharedModule {
}