import { Component } from '@angular/core';
import { Router } from '@angular/router';

import {CartComponent} from './cart/cart.component';

import { ToastService } from '../shared/toast.service';


@Component({
	selector: 'home-view',
	templateUrl: './home.component.html',
	styles: [`
		md-sidenav {
			width: 50em;
		}
	`]
})
export class HomeComponent{

	constructor(private router:Router, private toastService:ToastService) {}

	logout():void{
		this.router.navigate(['/welcome']);
		this.toastService.info('Goodbye', 'You have logged out');
	}

}
