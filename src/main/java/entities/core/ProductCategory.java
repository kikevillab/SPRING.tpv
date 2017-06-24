package entities.core;

import java.util.Arrays;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class ProductCategory extends CategoryComponent {

    @OneToOne
    @JoinColumn
    private Product product;

    public ProductCategory() {
        super();
    }

    public ProductCategory(Product product) {
        this.setProduct(product);
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
        this.setCode(product.getCode());
        this.setName(product.getReference());
        this.setIcon(product.getImage());
    }

    @Override
    public List<CategoryComponent> components() {
        return Arrays.asList(this);
    }

    @Override
    public void addComponent(CategoryComponent component) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Product product() {
        return getProduct();
    }

    @Override
    public String toString() {
        return super.toString() + "ProductCategory [productCode=" + product.getCode() + "]";
    }

}
