package controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import config.PersistenceConfig;
import config.TestsControllerConfig;
import config.TestsPersistenceConfig;
import entities.core.Product;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfig.class, TestsPersistenceConfig.class, TestsControllerConfig.class})
public class ProductControllerIT {

    @Autowired
    private ProductController productController;

    @Test
    public void testGetProductByCode() {
        String productCode = "84000002223";
        Product product = productController.getProductByCode(productCode);
        assertNotNull(product);
        assertEquals(productCode, product.getCode());
    }

    @Test
    public void testGetNonexistentProductByCode() {
        String productCode = "justTesting-123";
        Product product = productController.getProductByCode(productCode);
        assertNull(product);
    }

    @Test
    public void testExistentProductCode() {
        String productCode = "84000002223";
        assertTrue(productController.productCodeExists(productCode));
    }

    @Test
    public void testNonexistentProductCode() {
        String productCode = "justTesting-123";
        assertFalse(productController.productCodeExists(productCode));
    }
}
