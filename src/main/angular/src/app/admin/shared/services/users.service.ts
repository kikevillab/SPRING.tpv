/**
 * Created by fran lopez on 04/05/2017.
 */

import {Injectable} from '@angular/core';
import {Headers, URLSearchParams} from '@angular/http';
import {Session} from '../../../shared/models/session.model';
import {
    LOCAL_STORAGE_TOKEN_ATTRIBUTE,
    NOT_AUTHENTICATED_MESSAGE,
    ROLE_ADMIN
} from '../../../app.config';
import {User} from '../../../shared/models/user.model';
import {Observable} from 'rxjs/Observable';
import {HTTPService} from '../../../shared/services/http.service';
import {LocalStorageService} from '../../../shared/services/local-storage.service';
import 'rxjs/add/operator/map';
import {isNull} from "util";

@Injectable()
export class UsersService {
    private endpoint: string;
    private sessionToken: string;
    private headers: Headers;

    constructor(private httpService: HTTPService, private localStorageService: LocalStorageService) {
        this.headers = null;
        this.setEndpoint(null);
        this.setSessionToken('');
        this.init();
    }

    setEndpoint(endpoint: string): void {
        this.endpoint = endpoint;
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

    search(fieldName: string, fieldValue: any): Observable<any> {
        if (!isNull(this.headers) && !isNull(this.endpoint)) {
            let params = new URLSearchParams();
            params.set(fieldName, fieldValue);
            return this.httpService.get(this.endpoint, this.headers, params);
        }

        return Observable.throw(NOT_AUTHENTICATED_MESSAGE);
    }

    findAll(): Observable<any> {
        if (!isNull(this.headers) && !isNull(this.endpoint))
            return this.httpService.get(this.endpoint, this.headers);

        return Observable.throw(NOT_AUTHENTICATED_MESSAGE);
    }

    create(user: User): Observable<any> {
        if (!isNull(this.headers))
            return this.httpService.post(this.endpoint, user, this.headers);

        return Observable.throw(NOT_AUTHENTICATED_MESSAGE);
    }

    delete(mobile: number): Observable<any> {
        if (!isNull(this.headers))
            return this.httpService.delete(this.endpoint + '/' + mobile, this.headers);

        return Observable.throw(NOT_AUTHENTICATED_MESSAGE);
    }

    put(user: User): Observable<any> {
        if (!isNull(this.headers))
            return this.httpService.put(this.endpoint + '/' + user.id, user, this.headers);

        return Observable.throw(NOT_AUTHENTICATED_MESSAGE);
    }

}