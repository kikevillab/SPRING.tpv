package wrappers;

public class VoucherIdWrapper {
    int id;

    public VoucherIdWrapper(){}
    
    public VoucherIdWrapper(int id) {
        super();
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
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
        VoucherIdWrapper other = (VoucherIdWrapper) obj;
        if (id != other.id)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "VoucherIdWrapper [id=" + id + "]";
    }
}
