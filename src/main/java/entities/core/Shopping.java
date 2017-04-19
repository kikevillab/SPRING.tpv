package entities.core;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Shopping {
    @Id
    @GeneratedValue
    private int id;

    private int amount;

    private int discount;

    private long productId;

    private String description;

    private BigDecimal retailPrice;

    public Shopping() {
    }

    public Shopping(int amount, int discount, long productId, String description, BigDecimal retailPrice) {
        this.amount = amount;
        this.discount = discount;
        this.productId = productId;
        this.description = description;
        this.retailPrice = retailPrice;
    }

    public int getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
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

    @Override
    public int hashCode() {
        return id;
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
        return id == ((Shopping) obj).id;
    }

    @Override
    public String toString() {
        return "Shopping[" + id + ": amount=" + amount + ", discount=" + discount + ", productId=" + productId + ", description="
                + description + ", retailPrice=" + retailPrice + "]";
    }

}
