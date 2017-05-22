/**
  * @author Sergio Banegas Cortijo
  * Github: https://github.com/sergiobanegas 
*/
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { HomeComponent } from './home.component';
import { SearchComponent } from './search/search.component';
import { PaymentComponent } from './payment/payment.component';
import { OpenCashierComponent } from './open-cashier/open-cashier.component';
import { CloseCashierComponent } from './close-cashier/close-cashier.component';
import { MovementComponent } from './movement/movement.component';
import { TicketsOfTheDayComponent } from './tickets-of-the-day/tickets-of-the-day.component';


const routes: Routes = [
	{ 
	    path: 'home', 
	    component: HomeComponent,
	    children: [
	   		{ path: '', redirectTo: 'search', pathMatch: 'full' },
	   		{ path: 'search', component: SearchComponent },
	    	{ path: 'payment', component: PaymentComponent },
	    	{ path: 'opencashier', component: OpenCashierComponent },
	    	{ path: 'closecashier', component: CloseCashierComponent },
	    	{ path: 'movement', component: MovementComponent },
	    	{ path: 'ticketsoftheday', component: TicketsOfTheDayComponent }
	    ]
	},
];

@NgModule({
  imports: [ RouterModule.forChild(routes) ],
  exports: [ RouterModule ]
})
export class HomeRoutingModule { }
