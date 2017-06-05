package wrappers;

import entities.core.ProductCategory;

public class ProductCategoryWrapper extends CategoryComponentWrapper{
    ProductWrapper product;

    public ProductCategoryWrapper(){
        super();
    }
    
    public ProductCategoryWrapper(ProductWrapper product) {
        super();
        this.product = product;
    }
    
    public ProductCategoryWrapper(ProductCategory productCategory){
        this.product = new ProductWrapper(productCategory.getProduct());
    }

    public ProductWrapper getProduct() {
        return product;
    }

    public void setProduct(ProductWrapper product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "ProductCategoryWrapper [product=" + product + "]";
    }
    
}
