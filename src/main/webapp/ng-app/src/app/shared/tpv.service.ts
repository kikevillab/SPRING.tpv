import { Injectable } from '@angular/core';
import { Http, Response, Headers, RequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

import { TPV_API_URL } from '../app.config';

@Injectable()
export class TPVService {

	private options:RequestOptions = 
	new RequestOptions({ headers: new Headers({ 'Content-Type': 'application/json' }) }); 

	constructor (private http:Http){}

	requestGet(url:string): Observable<any> {
		return this.http.get(TPV_API_URL+url).map(this.extractData).catch(this.handleError);
	}

	requestPost(url:string, data:Object): Observable<any> {
		return this.http.post(TPV_API_URL+url, data, this.options).map(this.extractData).catch(this.handleError);
	}

	requestPut(url:string, data:Object): Observable<any> {
		return this.http.put(TPV_API_URL+url, data, this.options).map(this.extractData).catch(this.handleError);
	}

	requestDelete(url:string): Observable<any> {
		return this.http.delete(TPV_API_URL+url).map(this.extractData).catch(this.handleError);
	}

	private extractData(res: Response) {
		return res.json();
	}
	private handleError (error: Response | any) {
		return Observable.throw(error.message || error);
	}
}