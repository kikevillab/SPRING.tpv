import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { OrderTrackingComponent } from './order-tracking.component';

const routes: Routes = [
	{ 
        path: 'ordertracking/:reference', 
        component: OrderTrackingComponent 
    },
    { 
        path: 'ordertracking', 
        redirectTo: 'home' 
    }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class OrderTrackingRoutingModule { }
