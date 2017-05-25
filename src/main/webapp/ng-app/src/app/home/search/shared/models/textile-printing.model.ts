/**
  * @author Sergio Banegas Cortijo
  * Github: https://github.com/sergiobanegas
*/
import { Product } from '../../../../shared/models/product.model';

export class TextilePrinting extends Product {
    constructor(code: string, reference: string, description: string, retailPrice: number, discontinued: boolean, image: string, public type: string) {
    	super(code, reference, description, retailPrice, discontinued, image);
    }
}