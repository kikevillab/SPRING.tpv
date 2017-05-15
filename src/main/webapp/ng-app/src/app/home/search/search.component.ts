import { Component } from '@angular/core';

import { ToastService } from '../../shared/services/toast.service';

@Component({
  selector: 'search-view',
  templateUrl: './search.component.html',
  styles: [`

  `]
})

export class SearchComponent {


  constructor (private toastService: ToastService){

  }

}
