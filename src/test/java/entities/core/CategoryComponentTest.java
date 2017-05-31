package entities.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.BeforeClass;
import org.junit.Test;

public class CategoryComponentTest {

    private static CategoryComposite categoryComposite;

    @BeforeClass
    public static void setUpOnce() {
        categoryComposite = new CategoryComposite(null, "ROPA");
        categoryComposite.addCategoryComponent(new CategoryComposite(null, "CAMISAS"));
        categoryComposite.addCategoryComponent(
                new ProductCategory(new Product("84000015410", "sadJGHDshdjh", new BigDecimal(0.0), "Nike's Air Sneakers") {
                }));
    }

    @Test
    public void testToString() {
        assertEquals(
                "CategoryComponent [id=0, code=null, name=ROPA, icon=null"
                + "[ Component = CategoryComponent [id=0, code=null, name=CAMISAS, icon=null]"
                + "[ Component = CategoryComponent [id=0, code=84000015410, name=Nike's Air Sneakers, icon=null"
                + "Product [code=84000015410, reference=sadJGHDshdjh, description=Nike's Air Sneakers, retailPrice=0, discontinued=false, image=null]]]",
                categoryComposite.toString());
    }

    @Test
    public void testIsCategory() {
        assertTrue(categoryComposite.isCategory());
        assertTrue(categoryComposite.getCategoryComponents().get(0).isCategory());
        assertFalse(categoryComposite.getCategoryComponents().get(1).isCategory());
    }
}
