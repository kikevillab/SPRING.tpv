/**
 * Created by fran lopez on 02/05/2017.
 */

export const MOBILE_ATTRIBUTE_NAME: string = 'mobile';
export const DNI_ATTRIBUTE_NAME: string = 'dni';
export const EMAIL_ATTRIBUTE_NAME: string = 'email';

export class User {
    constructor(public mobile?: number, public password?: string, public dni?: string, public email?: string,
                public username?: string, public address?: string, public active?: boolean) {
    }
}