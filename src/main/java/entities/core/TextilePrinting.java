package entities.core;

import java.math.BigDecimal;

import javax.persistence.Entity;

@Entity
public class TextilePrinting extends Product {
    private String type;

    public TextilePrinting() {
    }

    public TextilePrinting(long id, String reference, BigDecimal retailPrice, String description, String type) {
        super(id, reference, retailPrice, description);
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
        return "TextilePrinting [" + super.toString() + ", type=" + type + ", toString()=" + super.toString() + "]";
    }

}
