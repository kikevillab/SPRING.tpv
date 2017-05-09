/**
 * Created by fran lopez on 07/05/2017.
 */

import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {WelcomeComponent} from './welcome.component';

const routes: Routes = [
    {
        path: 'welcome',
        component: WelcomeComponent
    },

    {
        path: '',
        redirectTo: 'welcome',
        pathMatch: 'full'
    }
];

export const routableComponents = [
    WelcomeComponent
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class WelcomeRoutingModule {
}