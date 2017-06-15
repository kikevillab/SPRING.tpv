/**
 * Created by fran lopez on 30/05/2017.
 */

import {Injectable} from '@angular/core';
import {Headers} from '@angular/http';
import {Session} from '../../../shared/models/session.model';
import {
    LOCAL_STORAGE_TOKEN_ATTRIBUTE,
    NOT_AUTHENTICATED_MESSAGE,
    ROLE_ADMIN
} from '../../../app.config';
import {Observable} from 'rxjs/Observable';
import {HTTPService} from '../../../shared/services/http.service';
import {LocalStorageService} from '../../../shared/services/local-storage.service';
import {SHOPPING_STATES_URI} from '../../admin.config';
import 'rxjs/add/operator/map';
import {isNull} from "util";

@Injectable()
export class ShoppingStatesService {
    private endpoint: string;
    private sessionToken: string;
    private headers: Headers;

    constructor(private httpService: HTTPService, private localStorageService: LocalStorageService) {
        this.headers = null;
        this.endpoint = SHOPPING_STATES_URI;
        this.setSessionToken('');
        this.init();
    }

    init(): void {
        if (this.localStorageService.isStored(LOCAL_STORAGE_TOKEN_ATTRIBUTE)) {
            let sessionString: string = this.localStorageService.getItem(
                LOCAL_STORAGE_TOKEN_ATTRIBUTE);
            let parsedSession = JSON.parse(sessionString);
            let session = new Session(parsedSession.token, parsedSession.rol);

            if (session.role === ROLE_ADMIN) {
                this.setSessionToken(parsedSession.token);
                this.setHeaders();
            }
        }
    }

    private setSessionToken(token: string) {
        this.sessionToken = token;
    }

    private setHeaders() {
        this.headers = new Headers({'Authorization': 'Basic ' + btoa(this.sessionToken + ':')});
    }

    findAll(): Observable<any> {
        if (!isNull(this.headers) && !isNull(this.endpoint))
            return this.httpService.get(this.endpoint, this.headers);

        return Observable.throw(NOT_AUTHENTICATED_MESSAGE);
    }
}