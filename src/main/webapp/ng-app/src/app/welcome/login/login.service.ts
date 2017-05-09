/**
 * Created by fran lopez on 04/05/2017.
 */

import {Injectable}    from '@angular/core';
import {Headers} from '@angular/http';
import {Token} from './token.model';
import {API_GENERIC_URI} from '../../app.config';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import {RequestService} from '../../shared/request.service';

@Injectable()
export class LoginService {
    private endpoint: string = API_GENERIC_URI + '/tokens';

    constructor(private requestService: RequestService) {
    }

    login(mobile: number, password: string): Observable<Token> {
        let headers = new Headers({
            'Authorization': 'Basic ' + btoa(mobile + ':' + password)
        });

        return this.requestService.post(this.endpoint, null, headers);
    }
}