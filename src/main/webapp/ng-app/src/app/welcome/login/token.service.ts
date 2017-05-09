/**
 * Created by fran lopez on 07/05/2017.
 */

import {Injectable} from '@angular/core';

@Injectable()
export class TokenService {

    login(token: string) {
        localStorage.setItem('token', token);
    }

    logout() {
        localStorage.removeItem('token');
    }

    getToken() {
        return localStorage.getItem('token');
    }

    thereIsToken(): boolean {
        return (this.getToken() !== null);
    }
}

