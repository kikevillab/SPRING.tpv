/**
  * @author Sergio Banegas Cortijo
  * Github: https://github.com/sergiobanegas
*/
import { Product } from '../../../../shared/models/product.model';
import { Article } from './article.model';
import { Embroidery } from './embroidery.model';
import { TextilePrinting } from './textile-printing.model';

export class ProductDetails {
	public product: Product;
    public type: string;
    constructor(public article?: Article, public embroidery?: Embroidery, public textilePrinting?: TextilePrinting) {
    	if (article) {
    		this.product = new Product(article.code, article.reference, article.description, article.retailPrice, article.discontinued, article.image);
            this.type = "Article";
        } else if (embroidery) {
    		this.product = new Product(embroidery.code, embroidery.reference, embroidery.description, embroidery.retailPrice, embroidery.discontinued, embroidery.image);
            this.type = "Embroidery";
        } else if (textilePrinting) {
    		this.product = new Product(textilePrinting.code, textilePrinting.reference, textilePrinting.description, textilePrinting.retailPrice, textilePrinting.discontinued, textilePrinting.image);
    	    this.type = "Textile printing";
        }
    }
}