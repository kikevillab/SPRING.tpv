import { Component, OnInit, OnDestroy, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { MdSidenav, MdDialog, MdDialogRef, MdDialogConfig } from '@angular/material';

import { CartComponent } from './cart/cart.component';
import { CashierClosure } from './shared/models/cashier-closure';
import { CashierService } from './shared/services/cashier.service';

import { ToastService } from '../shared/services/toast.service';

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
export class HomeComponent implements OnInit, OnDestroy {

	@ViewChild('cart') cartSidenav: MdSidenav;

	cartSideNavOpened: boolean = false;
	openedCashier: boolean = true;
	cashierSubscription: Subscription;

	constructor(private router: Router, private toastService: ToastService, private dialog: MdDialog, private cashierService: CashierService) {}

	ngOnInit(){
		this.cashierSubscription = this.cashierService.getCurrentCashierObservable().subscribe((currentCashier: CashierClosure) => {
	      this.openedCashier = currentCashier.closureDate == null;
	      (!this.openedCashier || currentCashier.openingDate == null) && this.router.navigate(['/home/opencashier']);
    	});
    	this.cashierService.initialize();
	}

	closeCartSidenav(): void {
		this.cartSideNavOpened = false;
	}

	openOrderTrackingDialog(): void {
		this.dialog.open(OrderTrackingDialog);
	}

	logout(): void {
		this.router.navigate(['/welcome']);
		this.toastService.info('Goodbye', 'You have logged out');
	}

	ngOnDestroy(){
		this.cashierSubscription.unsubscribe();
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

  ticketReferenceInput: string = '';

  constructor(public dialogRef: MdDialogRef<OrderTrackingDialog>, private router: Router) {}

  submitTicketReference(event: Event): void {
  	event.preventDefault();
  	this.dialogRef.close();
  	this.router.navigate(['/ordertracking', this.ticketReferenceInput]);
  }

}

