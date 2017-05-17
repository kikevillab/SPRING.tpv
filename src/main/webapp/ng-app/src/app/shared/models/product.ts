/**
  * @author Sergio Banegas Cortijo
  * Github: https://github.com/sergiobanegas 
*/
export class Product {
	constructor(public code: number, public reference: string, public description: string, 
		public retailPrice: number, public discontinued: boolean, public image: string) {}
}