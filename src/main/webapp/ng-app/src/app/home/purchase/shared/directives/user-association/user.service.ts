/**
  * @author Sergio Banegas Cortijo
  * Github: https://github.com/sergiobanegas 
*/
import { Injectable } from '@angular/core';
import { Headers } from '@angular/http';

import { API_GENERIC_URI, LOCAL_STORAGE_TOKEN_ATTRIBUTE } from '../../../../../app.config';

import { User } from '../../../../shared/models/user.model';

import { HTTPService } from '../../../../../shared/services/http.service';
import { TPVHTTPError } from '../../../../../shared/models/tpv-http-error.model';
import { LocalStorageService } from '../../../../../shared/services/local-storage.service';

@Injectable()
export class UserService {

  constructor (private httpService: HTTPService, private localStorageService: LocalStorageService) {}

  newUser(user: User): Promise<any> {
    let sessionString: string = this.localStorageService.getItem(LOCAL_STORAGE_TOKEN_ATTRIBUTE);
    let parsedSession = JSON.parse(sessionString);
    console.log(parsedSession.token);
    let headers = new Headers();
    headers.append('Authorization', 'Basic ' + btoa(parsedSession.token + ':'));
    return new Promise((resolve: Function, reject: Function) => {
      this.httpService.post(`${API_GENERIC_URI}/customers`, user, headers).subscribe(
        () => resolve(),
        (error: TPVHTTPError) => {reject(error.description);}
      );
    });
  }

}