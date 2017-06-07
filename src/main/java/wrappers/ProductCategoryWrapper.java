package wrappers;

import entities.core.CategoryComponent;
import entities.core.Product;

public class ProductCategoryWrapper extends CategoryComponentWrapper{
    private Product product;

    public ProductCategoryWrapper(){
        super();
    }
    
    public ProductCategoryWrapper(CategoryComponent categoryComponent) {
        super(categoryComponent);
        this.product = categoryComponent.product();
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "ProductCategoryWrapper [product=" + product + "]";
    }
    
}
