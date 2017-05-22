package wrappers;

public class PatchChangeDescriptionWrapper {
    private String op;

    private String path;

    private String value;

    public PatchChangeDescriptionWrapper(){
        
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "PatchRequestBodyWrapper [op=" + op + ", path=" + path + ", value=" + value + "]";
    }
}
