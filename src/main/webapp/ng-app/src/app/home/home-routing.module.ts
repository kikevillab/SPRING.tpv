import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { HomeComponent } from './home.component';
import { SearchComponent } from './search/search.component';
import { PaymentComponent } from './payment/payment.component';


const routes: Routes = [
	{ 
	    path: 'home', 
	    component: HomeComponent,
	    children: [
	   		{ path: '', redirectTo: 'search', pathMatch: 'full' },
	   		{ path: 'search', component: SearchComponent },
	    	{ path: 'payment', component: PaymentComponent }
	    ]
	},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class HomeRoutingModule { }
