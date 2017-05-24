/**
  * @author Sergio Banegas Cortijo
  * Github: https://github.com/sergiobanegas 
*/
import { Component } from '@angular/core';
import { MdDialog } from '@angular/material';

import { User } from '../../../shared/models/user.model';
import { ShoppingService } from '../../../shared/services/shopping.service';
import { ToastService } from '../../../../shared/services/toast.service';

@Component({
  selector: 'user-association-view',
  templateUrl: 'user-association.component.html',
  styles: [`
    #disassociateUserButton {
      position:absolute;
      right:0px;
      top:0px;
    }
    md-input-container {
      width:100%;
    }
  `]
})
export class UserAssociationComponent {

  userAssociated: User;
  mobileNumberInput: number;

  constructor(private shoppingService: ShoppingService, private toastService: ToastService) {}

  associate(event: Event): void {
    event.preventDefault();
    this.shoppingService.associateUser(this.mobileNumberInput).then((userAssociated: User) => {
      this.mobileNumberInput = null;
      this.userAssociated = userAssociated;
      this.toastService.success('Client asociated', `The client with the mobile ${userAssociated.mobile} has been associated`);
    }).catch((error: string) => {
      this.toastService.error('Error associating the client', error);
    });
  }

  disassociate(): void {
    this.toastService.info('Client dissasociated', `The client with the mobile ${this.mobileNumberInput} has been disassociated`);
    this.shoppingService.disassociateUser();
    this.userAssociated = null;
  }

}
