/**
  * @author Sergio Banegas Cortijo
  * Github: https://github.com/sergiobanegas 
*/
import { Injectable } from '@angular/core';

import { API_GENERIC_URI } from '../../../../../app.config';

import { User } from '../../../../shared/models/user.model';

import { HTTPService } from '../../../../../shared/services/http.service';
import { TPVHTTPError } from '../../../../../shared/models/tpv-http-error.model';

@Injectable()
export class UserService {

  constructor (private httpService: HTTPService) {}

  newUser(user: User): Promise<any> {
    return new Promise((resolve: Function, reject: Function) => {

      this.httpService.post(`${API_GENERIC_URI}/customers`, user).subscribe(
        () => resolve(),
        (error: TPVHTTPError) => {reject(error.description);}
      );
    });
  }

}