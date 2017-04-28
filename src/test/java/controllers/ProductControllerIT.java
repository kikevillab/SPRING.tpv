package controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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
    public void testGetProduct() {
        long productId = 84000001111L;
        Product product = productController.getProduct(productId);
        assertNotNull(product);
        assertEquals(productId, product.getId());
    }

    @Test
    public void testGetNonexistentProduct() {
        long productId = 3548455548455484L;
        Product product = productController.getProduct(productId);
        assertNull(product);
    }
}
