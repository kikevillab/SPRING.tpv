package entities.core;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Product {

    @Id
    private long id;

    @Column(unique = true, nullable = false)
    private String code;

    private String description;

    private BigDecimal retailPrice;

    private boolean discontinued;
    
    private String image;

    public Product() {
    }

    public Product(long id, String code, BigDecimal retailPrice, String description) {
        this.id = id;
        this.code = code;
        this.retailPrice = retailPrice;
        this.description = description;
        discontinued = false;
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
    public int hashCode() {
        return (int) id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        return id == ((Product) obj).id;
    }
    
    @Override
    public String toString() {
        return id + ": code=" + code + ", description=" + description + ", retailPrice=" + retailPrice + ", discontinued="
                + discontinued + "]";
    }

}
