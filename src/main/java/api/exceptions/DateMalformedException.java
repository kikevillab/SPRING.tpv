package api.exceptions;

public class DateMalformedException extends ApiException {
    private static final long serialVersionUID = 5296507526454478261L;

    public static final String DESCRIPTION = "Fecha mal formada";

    public static final int CODE = 16;

    public DateMalformedException(String rightDateFormat, String detail) {
        super(DESCRIPTION + ". " + "Debe usarse el formato: " + rightDateFormat + ". " + detail, CODE);
    }

}
