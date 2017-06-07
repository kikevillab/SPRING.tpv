/**
  * @author Sergio Banegas Cortijo
  * Github: https://github.com/sergiobanegas 
*/
import { Injectable } from '@angular/core';
import { Headers, URLSearchParams } from '@angular/http';

import { LOCAL_STORAGE_TOKEN_ATTRIBUTE, URI_USERS, ROLE_CUSTOMER } from '../../../../../app.config';

import { User } from '../../../../shared/models/user.model';
import { HTTPService } from '../../../../../shared/services/http.service';
import { TPVHTTPError } from '../../../../../shared/models/tpv-http-error.model';
import { LocalStorageService } from '../../../../../shared/services/local-storage.service';

@Injectable()
export class UserService {

  constructor (private httpService: HTTPService, private localStorageService: LocalStorageService) {}

  newUser(user: User): Promise<any> { 
    return new Promise((resolve: Function, reject: Function) => {
      let params: URLSearchParams = new URLSearchParams();
      params.append('role', ROLE_CUSTOMER);
      this.httpService.post(`${URI_USERS}`, user, null, params).subscribe(
        () => resolve(user),
        (error: TPVHTTPError) => {reject(error.description);}
      );
    });
  }

}