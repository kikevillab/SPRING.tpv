import { Component, OnDestroy, NgModule } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { MdDialog } from '@angular/material';

import { TPVService } from '../../shared/tpv.service';
import { ToastService } from '../../shared/toast.service';

@Component({
  selector: 'search-view',
  templateUrl: './search.component.html',
  styles: [`

  `]
})

export class SearchComponent {


  constructor (private toastService: ToastService, private tpvService: TPVService){

  }

}
