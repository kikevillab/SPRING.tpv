/**
  * @author Sergio Banegas Cortijo
  * Github: https://github.com/sergiobanegas
*/
import { Component, OnInit, OnDestroy, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { MdSidenav, MdDialog, MdDialogRef, MdDialogConfig } from '@angular/material';

import { ShoppingCartComponent } from './shopping-cart/shopping-cart.component';
import { HomeService } from './home.service';
import { CashierClosure } from './shared/models/cashier-closure.model';
import { CashierService } from './shared/services/cashier.service';
import { AuthService } from './shared/services/auth.service';
import { ToastService } from '../shared/services/toast.service';

@Component({
    selector: 'home-view',
    templateUrl: './home.component.html',
    styles: [`
		@media only screen and (max-width: 600px) {
			md-sidenav {
				width: 100%;
			}
		}

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
    authorizationSubscription: Subscription;

    constructor(private router: Router, private toastService: ToastService, private dialog: MdDialog, private cashierService: CashierService, private homeService: HomeService, private authService: AuthService) { }

    ngOnInit() {
        this.authorizationSubscription = this.authService.getAuthorizationObservable().subscribe((authorized: boolean) => {
            !authorized && this.logout();
        });
        this.cashierSubscription = this.cashierService.getCurrentCashierObservable().subscribe((currentCashier: CashierClosure) => {
            this.openedCashier = currentCashier.closureDate == null;
            (!this.openedCashier || currentCashier.openingDate == null) && this.router.navigate(['/home/open-cashier']);
        });
        this.cashierService.initialize();
    }

    closeCartSidenav(): void {
        this.cartSideNavOpened = false;
    }

    openOrderTrackingDialog(): void {
        this.dialog.open(OrderTrackingDialog);
    }

    onClickLogout(): void {
        this.toastService.info('Goodbye', 'You have logged out');
        this.logout();
    }

    private logout(): void {
        this.homeService.logout();
        this.router.navigate(['/welcome']);
    }

    ngOnDestroy() {
        this.cashierSubscription && this.cashierSubscription.unsubscribe();
    }

}

@Component({
    selector: 'order-tracking-dialog-view',
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
    `,
    styles: [`
        button {
          display: block;
          margin: 0 auto;
        }
    `]
})
export class OrderTrackingDialog {

    ticketReferenceInput: string = '';

    constructor(public dialogRef: MdDialogRef<OrderTrackingDialog>, private router: Router) { }

    submitTicketReference(event: Event): void {
        event.preventDefault();
        this.dialogRef.close();
        this.router.navigate(['/order-tracking', this.ticketReferenceInput]);
    }

}