/**
 * Created by fran lopez on 07/05/2017.
 */

import {Injectable}    from '@angular/core';
import {Http, Headers, Response, RequestOptions} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/map';

@Injectable()
export class RequestService {
    constructor(private http: Http) {
    }

    public post(endpoint: string, body: Object, headers: Headers): Observable<any> {
        let options = new RequestOptions({headers: headers});

        return this.http
            .post(endpoint, body, options)
            .map(this.extractData)
            .catch(this.handleError);
    }

    private extractData(response: Response) {
        let responseBody = response.json();
        return responseBody || {};
    }

    private handleError(error: Response | any) {
        let errMsg: string;

        if (error instanceof Response) {
            try {
                const body = error.json() || '';
                errMsg = `${body.error.error} - ${body.error.description}`;
            }
            catch (exception) {
                errMsg = error.statusText;
            }
        } else
            errMsg = error.message ? error.message : error.toString();

        console.error(errMsg);
        return Promise.reject(errMsg);
    }
}