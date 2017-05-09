import { TestBed, async, inject } from '@angular/core/testing';
import { HttpModule, Http, Response, ResponseOptions, XHRBackend } from '@angular/http';
import { MockBackend } from '@angular/http/testing';

import { TPVService } from './tpv.service';
import { API_GENERIC_URI } from '../app.config';

describe('Service: TPVService', () => {

	beforeEach(() => {
		TestBed.configureTestingModule({
			imports: [HttpModule],
			providers: [
			{ provide: API_GENERIC_URI, useValue: 'http://fakeurl.com' },
			TPVService,
			{ provide: XHRBackend, useClass: MockBackend },
			MockBackend
			]
		});
	});

	it(`'requestGet()' should return an Observable<Array<any>>`,
		inject([TPVService, MockBackend], (tpvService, mockBackend) => {
			const mockResponse = {
				data: [
				{ id: 0, code: 'article0' },
				{ id: 1, code: 'article1' },
				{ id: 2, code: 'article2' },
				{ id: 3, code: 'article3' },
				]
			};
			mockBackend.connections.subscribe(connection => {
				connection.mockRespond(new Response(new ResponseOptions({
					body: JSON.stringify(mockResponse)
				})));
			});
			tpvService.requestGet('/products').subscribe(products => {
				expect(products.length).toBe(4);
				expect(products[0].code).toEqual('CODE1');
				expect(products[1].code).toEqual('CODE2');
				expect(products[2].code).toEqual('CODE3');
				expect(products[3].code).toEqual('CODE4');
			});
		}));

	it(`'requestPost()' should return an Observable<any>`,
		inject([TPVService, MockBackend], (tpvService:TPVService, mockBackend:MockBackend) => {
			const mockResponse = {
				data: [
				{ id: 0, code: 'article0' },
				{ id: 1, code: 'article1' },
				{ id: 2, code: 'article2' },
				{ id: 3, code: 'article3' },
				]
			};
			mockBackend.connections.subscribe(connection => {
				connection.mockRespond(new Response(new ResponseOptions({
					body: JSON.stringify(mockResponse)
				})));
			});
			let postValue:Object = { id: 0, name: 'article0' };
			tpvService.requestPost('/products', postValue).subscribe(product => {
				expect(product).not.toBeNull();
				expect(product.code).toEqual('article0');
				expect(product.id).toEqual(0);
			});
		}));

	it(`'requestUpdate()' should return an Observable<any>`,
		inject([TPVService, MockBackend], (tpvService:TPVService, mockBackend:MockBackend) => {
			const mockResponse = {
				data: [
				{ id: 0, code: 'article0' },
				{ id: 1, code: 'article1' },
				{ id: 2, code: 'article2' },
				{ id: 3, code: 'article3' },
				]
			};
			mockBackend.connections.subscribe(connection => {
				connection.mockRespond(new Response(new ResponseOptions({
					body: JSON.stringify(mockResponse)
				})));
			});
			let updateValue = { id: 0, name: 'article4' };
			tpvService.requestPut('/products/0', updateValue).subscribe(product => {
				expect(product).not.toBeNull();
				expect(product.code).toEqual('article4');
				expect(product.id).toEqual(0);
			});
		}));

	it(`'requestDelete()' should return an Observable<any>`,
		inject([TPVService, MockBackend], (tpvService:TPVService, mockBackend:MockBackend) => {
			const mockResponse = {
				data: [
				{ id: 0, code: 'article0' },
				{ id: 1, code: 'article1' },
				{ id: 2, code: 'article2' },
				{ id: 3, code: 'article3' }
				]
			};
			mockBackend.connections.subscribe(connection => {
				connection.mockRespond(new Response(new ResponseOptions({
					body: JSON.stringify(mockResponse)
				})));
			});
			let updateValue = { id: 0, name: 'article3' };
			tpvService.requestDelete('/products/0').subscribe(response => {
				expect(response).not.toBeNull();
			});
		}));

});