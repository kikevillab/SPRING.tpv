/**
  * @author Sergio Banegas Cortijo
  * Github: https://github.com/sergiobanegas
*/
import { Product } from '../../../../shared/models/product.model';

export class Embroidery extends Product {
    constructor(code: string, reference: string, description: string, retailPrice: number, discontinued: boolean, image: string, public stitches: number, public colors: number, public squareMillimeters: number) {
    	super(code, reference, description, retailPrice, discontinued, image);
    }
}