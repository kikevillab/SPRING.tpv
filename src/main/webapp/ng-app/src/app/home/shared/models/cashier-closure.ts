export class CashierClosure {
	constructor(public id: number, public amount: number, public openingDate: Date, public closureDate: Date, comment: string) {}

	isOpened(): boolean {
		return this.closureDate != null;
	}
}