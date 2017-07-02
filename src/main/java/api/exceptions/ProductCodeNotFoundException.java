package api.exceptions;

public class ProductCodeNotFoundException extends ApiException {

    private static final long serialVersionUID = -5670907548890543981L;

    public static final String DESCRIPTION = "Código de producto no encontrado";

    public static final int CODE = 7;

    public ProductCodeNotFoundException() {
        this("");
    }

    public ProductCodeNotFoundException(String detail) {
        super(DESCRIPTION + ". " + detail, CODE);
    }

}
