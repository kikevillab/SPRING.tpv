package entities.core;

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
                    @Override
                    public String getLongDescription() {
                        return "";
                    }
                }));
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

                    @Override
                    public String getLongDescription() {
                        return "";
                    }
                }));
        assertTrue(categoryRoot.components().size() == 2);
        CategoryComposite pantalones = new CategoryComposite(null, "PANTALONES");
        pantalones.addCategoryComponent(new ProductCategory(new Product("84000012345", "AdsJKGHHJdsdfd", new BigDecimal(25.00), "Pepe Jeans Slim 48"){

            @Override
            public String getLongDescription() {
                return "";
            }}));
        categoryRoot.addComponent(pantalones);
        assertTrue(categoryRoot.components().size() == 3);
    }
}
