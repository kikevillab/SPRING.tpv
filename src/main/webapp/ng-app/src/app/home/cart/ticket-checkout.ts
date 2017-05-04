import { CartProduct } from './cart-product';

export class TicketCheckout {
	userMobile: number;
	shoppingList: CartProduct[];
	constructor(shoppingList: CartProduct[], userMobile?:number) {
		this.shoppingList = shoppingList;
		this.userMobile = userMobile;
	}

}