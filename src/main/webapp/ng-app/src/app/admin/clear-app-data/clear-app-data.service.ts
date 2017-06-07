/**
 * Created by fran lopez on 04/05/2017.
 */

import {Injectable} from '@angular/core';
import {Headers} from '@angular/http';
import {Session} from '../../shared/models/session.model';
import {
    LOCAL_STORAGE_TOKEN_ATTRIBUTE,
    NOT_AUTHENTICATED_MESSAGE,
    ROLE_ADMIN,
    API_GENERIC_URI
} from '../../app.config';
import {Observable} from 'rxjs/Observable';
import {HTTPService} from '../../shared/services/http.service';
import {LocalStorageService} from '../../shared/services/local-storage.service';
import 'rxjs/add/operator/map';
import {isNull} from "util";

@Injectable()
export class ClearAppDataService {
    private endpoint: string = API_GENERIC_URI + '/admins';
    private sessionToken: string;
    private headers: Headers;

    constructor(private httpService: HTTPService, private localStorageService: LocalStorageService) {
        this.headers = null;
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

    clearData(): Observable<any> {
        if (!isNull(this.headers))
            return this.httpService.delete(this.endpoint, this.headers);

        return Observable.throw(NOT_AUTHENTICATED_MESSAGE);
    }
}