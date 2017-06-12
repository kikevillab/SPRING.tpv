package entities.core;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Article extends Product {
    private int stock;

    private BigDecimal wholesalePrice;

    @ManyToOne
    @JoinColumn
    private Provider provider;

    public Article() {
    }

    public Article(String code, String reference, BigDecimal retailPrice, String description, BigDecimal wholesalePrice, Provider provider) {
        super(code, reference, retailPrice, description);
        this.stock = 0;
        this.wholesalePrice = wholesalePrice;
        this.provider = provider;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public BigDecimal getWholesalePrice() {
        return wholesalePrice;
    }

    public void setWholesalePrice(BigDecimal wholesalePrice) {
        this.wholesalePrice = wholesalePrice;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }
    
    @Override
    public String getLongDescription() {
        return "Article with " + stock + " units in stock and sold at a wholesale price of €" + wholesalePrice + ".";
    }

    @Override
    public String toString() {
        return "Article [" + super.toString() + "stock=" + stock + ", wholesalePrice=" + wholesalePrice + ", providerId=" + provider.getId()
                + "]";
    }

}
