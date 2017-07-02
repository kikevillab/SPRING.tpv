package api.exceptions;

public class FileNameNotFoundException extends ApiException {

    private static final long serialVersionUID = 3886231640831810298L;

    public static final String DESCRIPTION = "Nombre de fichero no encontrado";

    public static final int CODE = 6;

    public FileNameNotFoundException() {
        this("");
    }

    public FileNameNotFoundException(String detail) {
        super(DESCRIPTION + ". " + detail, CODE);
    }

}
