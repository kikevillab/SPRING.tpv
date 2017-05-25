/**
  * @author Sergio Banegas Cortijo
  * Github: https://github.com/sergiobanegas
*/
import { Product } from '../../../../shared/models/product.model';

export class Article extends Product {
    constructor(code: string, reference: string, description: string, retailPrice: number, discontinued: boolean, image: string, public stock: number, public wholesalePrice: number) {
    	super(code, reference, description, retailPrice, discontinued, image);
    }
}