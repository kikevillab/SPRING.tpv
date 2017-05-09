package api.exceptions;

public class InvalidProductDiscountException extends ApiException {
    private static final long serialVersionUID = -6099784745820921879L;

    public static final String DESCRIPTION = "Descuento de producto inferior a 0% o superior a 100%";

    public static final int CODE = 15;

    public InvalidProductDiscountException() {
        this("");
    }

    public InvalidProductDiscountException(String detail) {
        super(DESCRIPTION + ". " + detail, CODE);
    }

}
