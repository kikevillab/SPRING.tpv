package wrappers;

public class AmountWrapper {
    
    private double amount;
    
    public AmountWrapper(){
    }
    
    public AmountWrapper(double amount) {
        super();
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(amount);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AmountWrapper other = (AmountWrapper) obj;
        if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "AmountWrapper [amount=" + amount + "]";
    }
    
}
