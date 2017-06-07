/**
 * Created by fran lopez on 30/05/2017.
 */

export const REFERENCE_ATTRIBUTE_NAME: string = 'reference';

export class Ticket {
    constructor(public created?: string, public reference?: string) {
    }
}