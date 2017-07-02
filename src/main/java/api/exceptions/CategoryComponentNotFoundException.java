package api.exceptions;

public class CategoryComponentNotFoundException extends ApiException {

    private static final long serialVersionUID = -4101618007964666528L;

    private static final String DESCRIPTION = "Nombre de categoria no encontrado";

    private static final int CODE = 31;

    public CategoryComponentNotFoundException() {
        this("");
    }
    public CategoryComponentNotFoundException(String detail) {
        super(DESCRIPTION + ". " + detail, CODE);
    }
    

}
