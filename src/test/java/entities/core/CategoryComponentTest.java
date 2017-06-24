package entities.core;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.BeforeClass;
import org.junit.Test;

public class CategoryComponentTest {

    private static CategoryComponent categoryComponent;

    @BeforeClass
    public static void setUpOnce() {
        categoryComponent = new CategoryComposite(null, "ROPA");
        categoryComponent.addComponent(new CategoryComposite(null, "CAMISAS"));
        categoryComponent.addComponent(
                new ProductCategory(new Product("84000015410", "sadJGHDshdjh", new BigDecimal(0.0), "Nike's Air Sneakers") {
                    @Override
                    public String getLongDescription() {
                        return "";
                    }
                }));
    }

    @Test
    public void testIsCategory() {
        assertTrue(categoryComponent.isCategory());
    }
    
    @Test
    public void testComponents(){
        assertTrue(categoryComponent.components().size() == 2);
    }
}
