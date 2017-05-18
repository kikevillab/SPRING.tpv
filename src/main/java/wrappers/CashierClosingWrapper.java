package wrappers;

public class CashierClosingWrapper {
    
    private double amount;
    
    private String comment;
    
    public CashierClosingWrapper(){
    }
    
    public CashierClosingWrapper(double amount, String comment) {
        super();
        this.amount = amount;
        this.comment = comment;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(amount);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + ((comment == null) ? 0 : comment.hashCode());
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
        CashierClosingWrapper other = (CashierClosingWrapper) obj;
        if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
            return false;
        if (comment == null) {
            if (other.comment != null)
                return false;
        } else if (!comment.equals(other.comment))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "CashierClosingWrapper [amount=" + amount + ", comment=" + comment + "]";
    }

    
}
