
import { Injectable } from '@angular/core';
import { Router, CanActivate } from '@angular/router';

import { LocalStorageService } from '../shared/services/local-storage.service';
import { Session } from '../shared/models/session.model';
import { LOCAL_STORAGE_TOKEN_ATTRIBUTE, ROLE_ADMIN, ROLE_MANAGER, ROLE_OPERATOR } from '../app.config';

@Injectable()
export class HomeGuard implements CanActivate {
  constructor(private router: Router, private localStorageService: LocalStorageService) {}

  canActivate() {
    if (this.localStorageService.isStored(LOCAL_STORAGE_TOKEN_ATTRIBUTE)) {
      let sessionString: string = this.localStorageService.getItem(
          LOCAL_STORAGE_TOKEN_ATTRIBUTE);
      let parsedSession: any = JSON.parse(sessionString);
      let session: Session = new Session(parsedSession.token, parsedSession.rol);
      if (session.role === ROLE_ADMIN || session.role === ROLE_MANAGER || session.role === ROLE_OPERATOR) {
        return true
      }
    }
    this.router.navigate(['/welcome']);
    return false;
  }
}