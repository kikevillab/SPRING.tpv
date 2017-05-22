/**
  * @author Sergio Banegas Cortijo
  * Github: https://github.com/sergiobanegas 
*/
export class Voucher {
	constructor(public reference: string, public value: number, public created: Date, public dateOfUse: Date) {}
}