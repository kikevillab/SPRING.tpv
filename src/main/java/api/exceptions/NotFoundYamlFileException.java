package api.exceptions;

public class NotFoundYamlFileException extends ApiException {

    private static final long serialVersionUID = 3886231640831810298L;

    public static final String DESCRIPTION = "Fichero YAML no encontrado";

    public static final int CODE = 6;

    public NotFoundYamlFileException() {
        this("");
    }

    public NotFoundYamlFileException(String detail) {
        super(DESCRIPTION + ". " + detail, CODE);
    }

}
