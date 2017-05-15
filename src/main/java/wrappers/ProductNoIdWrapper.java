package wrappers;

import java.math.BigDecimal;

public class ProductNoIdWrapper {
    private String code;

    private String description;

    private BigDecimal retailPrice;

    private boolean discontinued;
    
    private String image;
    
    public ProductNoIdWrapper(){}

    public ProductNoIdWrapper(String code, String description, BigDecimal retailPrice, boolean discontinued, String image) {
        super();
        this.code = code;
        this.description = description;
        this.retailPrice = retailPrice;
        this.discontinued = discontinued;
        this.image = image;
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
        return "ProductNoIdWrapper [code=" + code + ", description=" + description + ", retailPrice=" + retailPrice + ", discontinued="
                + discontinued + ", image=" + image + "]";
    }
    
}
