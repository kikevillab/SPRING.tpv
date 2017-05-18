/**
 * Created by fran lopez on 11/05/2017.
 */

import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {SharedModule} from '../shared/shared.module';
import {CustomersModule} from './customers/customers.module';
import {AdminComponent} from './admin.component';
import {TicketsComponent} from './tickets/tickets.component';
import {ProductsComponent} from './products/products.component';
import {CategoriesComponent} from './categories/categories.component';
import {CashierClosuresComponent} from './cashier-closures/cashier-closures.component';
import {MovementsComponent} from './movements/movements.component';
import {ProvidersComponent} from './providers/providers.component';
import {OperatorsComponent} from './operators/operators.component';
import {ManagersComponent} from './managers/managers.component';
import {StatisticsComponent} from './statistics/statistics.component';
import {AdminRoutingModule} from './admin.routes';
import {AngularMaterialModule} from '../shared/angular-material.module';
import {AdminGuard} from './admin.guard';

@NgModule({
    declarations: [
        AdminComponent,
        TicketsComponent,
        ProductsComponent,
        CategoriesComponent,
        CashierClosuresComponent,
        MovementsComponent,
        ProvidersComponent,
        OperatorsComponent,
        ManagersComponent,
        StatisticsComponent
    ],
    imports: [
        CommonModule,
        AdminRoutingModule,
        AngularMaterialModule,
        SharedModule,
        CustomersModule
    ],
    providers: [
        AdminGuard
    ],
})
export class AdminModule {
}