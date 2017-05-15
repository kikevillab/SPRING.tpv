package wrappers;

public class TextilePrintingCreationWrapper extends ProductNoIdWrapper{

    private String type;

    public TextilePrintingCreationWrapper(){
        
    }
    
    public TextilePrintingCreationWrapper(String type) {
        super();
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "TextilePrintingCreationWrapper [type=" + type + "]";
    }
    
}
