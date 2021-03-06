package wrappers;

import java.math.BigDecimal;

public class ArticleWrapper extends ProductWrapper {

    private int stock;

    private BigDecimal wholesalePrice;

    public ArticleWrapper() {
        super();
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

    @Override
    public String toString() {
        return "ArticleWrapper [stock=" + stock + ", wholesalePrice=" + wholesalePrice + "]";
    }
}
