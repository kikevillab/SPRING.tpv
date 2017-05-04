export class CartProduct {
	productCode: string;
	description: string;
	retailPrice: number;
	discount: number;
	amount: number;
	delivered: boolean;
	constructor(productCode: string, description: string, retailPrice: number) {
		this.productCode = productCode;
		this.description = description;
		this.retailPrice = retailPrice;
		this.discount = 0;
		this.amount = 1;
		this.delivered = true;
	}

	

}