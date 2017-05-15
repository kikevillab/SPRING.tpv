import { Component, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';

import { CashierClosure } from '../shared/models/cashier-closure';
import { CashierService } from '../shared/services/cashier.service';
import { ToastService } from '../../shared/services/toast.service';

@Component({
  selector: 'open-cashier-view',
  templateUrl: './open-cashier.component.html'
})

export class OpenCashierComponent implements OnDestroy {

	private cashierClosureDate: Date = this.cashierService.getCurrentCashier().closureDate;
	private cashierSubscription: Subscription;

	constructor(private router: Router, private cashierService: CashierService, private toastService: ToastService){
		this.cashierSubscription = this.cashierService.getCurrentCashierObservable().subscribe((currentCashier: CashierClosure) => {
			currentCashier.closureDate && this.router.navigate(['/home']);
	      	this.cashierClosureDate = currentCashier.closureDate;
    	});
	}

	open():void {
		this.cashierService.openCashier().then((cashier: CashierClosure) => {
			this.toastService.info('Cashier opened', "The cashier has been opened and it's now available");
			this.router.navigate(['/home']);
		}).catch((error: string) => {
			this.toastService.error('Error opening cashier', error);
		});	
	}

	ngOnDestroy(){
		this.cashierSubscription.unsubscribe();
	}

}