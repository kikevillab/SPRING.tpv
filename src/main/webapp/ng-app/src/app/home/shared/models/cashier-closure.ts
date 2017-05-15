export class CashierClosure {
	constructor(public id: number = 0, public amount: number = 0, public openingDate: Date = new Date(), public closureDate: Date = null, comment: string = null) {}

	isOpened(): boolean {
		return this.closureDate != null;
	}
}