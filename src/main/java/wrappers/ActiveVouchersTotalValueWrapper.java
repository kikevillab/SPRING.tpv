package wrappers;

import java.math.BigDecimal;

public class ActiveVouchersTotalValueWrapper {
    BigDecimal totalValue;

    public ActiveVouchersTotalValueWrapper(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }

    public BigDecimal getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }

    @Override
    public String toString() {
        return "ActiveVouchersTotalValueWrapper [totalValue=" + totalValue + "]";
    }
}
