package wrappers;

import java.math.BigDecimal;

import entities.core.Product;

/**
 * Necessary wrapper because Product is an abstract class.
 *
 */
public class ProductWrapper {
    
    private String code;
    
    private String reference;

    private String description;

    private BigDecimal retailPrice;

    private boolean discontinued;
    
    private String image;

    public ProductWrapper() {
    }

    public ProductWrapper(Product product) {
        this.code = product.getCode();
        this.reference = product.getReference();
        this.retailPrice = product.getRetailPrice();
        this.description = product.getDescription();
        this.discontinued = product.isDiscontinued();
        this.image = product.getImage();
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

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    @Override
    public String toString() {
        return "ProductWrapper [code=" + code + ", reference=" + reference + ", description=" + description + ", retailPrice=" + retailPrice
                + ", discontinued=" + discontinued + ", image=" + image + "]";
    }

}
