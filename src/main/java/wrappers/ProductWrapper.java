package wrappers;

import java.math.BigDecimal;

import entities.core.Product;

/**
 * Necessary wrapper because Product is an abstract class.
 *
 */
public class ProductWrapper {
    
    private long id;

    private String code;

    private String description;

    private BigDecimal retailPrice;

    private boolean discontinued;
    
    private String image;

    public ProductWrapper() {
    }

    public ProductWrapper(Product product) {
        this.id = product.getId();
        this.code = product.getCode();
        this.retailPrice = product.getRetailPrice();
        this.description = product.getDescription();
        this.discontinued = product.isDiscontinued();
        this.image = product.getImage();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(BigDecimal retailPrice) {
        this.retailPrice = retailPrice;
    }

    public boolean isDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(boolean discontinued) {
        this.discontinued = discontinued;
    }
    
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return id + ": code=" + code + ", description=" + description + ", retailPrice=" + retailPrice + ", discontinued=" + discontinued 
                +  ", image=" + image +  "]";
    }

}
