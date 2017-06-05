package entities.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.BeforeClass;
import org.junit.Test;

public class CategoryComponentTest {

    private static CategoryComponent categoryComposite;

    @BeforeClass
    public static void setUpOnce() {
        categoryComposite = new CategoryComposite(null, "ROPA");
        categoryComposite.addComponent(new CategoryComposite(null, "CAMISAS"));
        categoryComposite.addComponent(
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
        assertTrue(categoryComposite.components().get(0).isCategory());
        assertFalse(categoryComposite.components().get(1).isCategory());
    }
    
    @Test
    public void testComponents(){
        CategoryComponent categoryRoot = new CategoryComposite(null, "ROPA");
        categoryRoot.addComponent(new CategoryComposite(null, "CAMISAS"));
        categoryRoot.addComponent(
                new ProductCategory(new Product("84000015410", "sadJGHDshdjh", new BigDecimal(0.0), "Nike's Air Sneakers") {
                }));
        assertTrue(categoryRoot.components().size() == 2);
        CategoryComposite pantalones = new CategoryComposite(null, "PANTALONES");
        pantalones.addCategoryComponent(new ProductCategory(new Product("84000012345", "AdsJKGHHJdsdfd", new BigDecimal(25.00), "Pepe Jeans Slim 48"){}));
        categoryRoot.addComponent(pantalones);
        assertTrue(categoryRoot.components().size() == 3);
    }
}
