package api.exceptions;

public class HeaderMalformedException extends ApiException {

    private static final long serialVersionUID = -1344640670884805385L;

    public static final String DESCRIPTION = "Autorización de cabecera Auth Basic mal formada";

    public static final int CODE = 3;

    public HeaderMalformedException() {
        this("");
    }

    public HeaderMalformedException(String detail) {
        super(DESCRIPTION + ". " + detail, CODE);
    }

}
