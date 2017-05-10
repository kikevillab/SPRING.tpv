import { Component, ViewChild } from '@angular/core';
import { Router } from '@angular/router';

import { MdSidenav, MdDialog, MdDialogRef } from '@angular/material';

import {CartComponent} from './cart/cart.component';

import { ToastService } from '../shared/toast.service';


@Component({
	selector: 'home-view',
	templateUrl: './home.component.html',
	styles: [`
		@media only screen and (min-width: 768px) {
			md-sidenav {
				width: 45em;
			}
		}

		@media only screen and (min-width: 1024px) {
			md-sidenav {
				width: 50em;
			}
		}
	`]
})
export class HomeComponent{

	@ViewChild('cart') cartSidenav: MdSidenav;

	cartSideNavOpened:boolean = false;

	constructor(private router:Router, private toastService:ToastService, private dialog: MdDialog) {}

	closeCartSidenav(){
		this.cartSideNavOpened= false;
	}

	openOrderTrackingDialog(){
		this.dialog.open(OrderTrackingDialog);
	}

	logout():void{
		this.router.navigate(['/welcome']);
		this.toastService.info('Goodbye', 'You have logged out');
	}

}

@Component({
  selector: 'order-tracking-dialog',
  template: `
	<form (ngSubmit)="submitTicketReference($event)" #ticketReferenceForm="ngForm">
		<div class="form-group">
			<md-input-container >
				<input mdInput placeholder="Ticket reference" maxLength="255" class="form-control" id="inputTicketReference" required
				[(ngModel)]="ticketReferenceInput" name="inputTicketReference" #name="ngModel">
			</md-input-container>
			<button md-raised-button color="primary" type="submit" [disabled]="!ticketReferenceForm.form.valid" id="submitTicketReferenceButton">OK</button>
		</div>
	</form>
  `, styles: [`
    button {
      display: block;
      margin: 0 auto;
    }
  `]
})
export class OrderTrackingDialog {

  ticketReferenceInput:string = '';

  constructor(public dialogRef: MdDialogRef<OrderTrackingDialog>, private router:Router) {}

  submitTicketReference(event:Event) {
  	event.preventDefault();
  	this.dialogRef.close();
  	this.router.navigate(['/ordertracking', this.ticketReferenceInput]);
  }

}
