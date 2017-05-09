import { Component, ViewChild } from '@angular/core';
import { Router } from '@angular/router';

import { MdSidenav } from '@angular/material';


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

	@ViewChild('cart') cartSidenav: MdSidenav;

	cartSideNavOpened:boolean = false;

	constructor(private router:Router, private toastService:ToastService) {}

	closeCartSidenav(){
		this.cartSideNavOpened= false;
	}

	logout():void{
		this.router.navigate(['/welcome']);
		this.toastService.info('Goodbye', 'You have logged out');
	}

}
