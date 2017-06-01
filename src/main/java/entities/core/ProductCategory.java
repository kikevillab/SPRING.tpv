package entities.core;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class ProductCategory extends CategoryComponent {
    
    @OneToOne
    @JoinColumn
    private Product product;
    
    public ProductCategory(){
        super();
    }
    public ProductCategory(Product product){
        super(product.getCode(), product.getDescription());
        this.product = product;     
    }
    
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    protected String print() {
        return product.toString();
    }

    
}
