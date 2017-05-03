package api.exceptions;

public class NotFoundProductIdException extends ApiException {

    private static final long serialVersionUID = -5670907548890543981L;

    public static final String DESCRIPTION = "No se encuentra el id de producto utilizado";

    public static final int CODE = 9;

    public NotFoundProductIdException() {
        this("");
    }

    public NotFoundProductIdException(String detail) {
        super(DESCRIPTION + ". " + detail, CODE);
    }

}
