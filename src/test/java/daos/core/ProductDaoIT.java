package daos.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import config.PersistenceConfig;
import config.TestsPersistenceConfig;
import entities.core.Product;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfig.class, TestsPersistenceConfig.class})
public class ProductDaoIT {

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private EmbroideryDao embroideryDao;

    @Autowired
    private TextilePrintingDao textilePrintingDao;
    
    @Autowired
    private ProductDao productDao;

    @Test
    public void testCreateArticle() {
        assertEquals(8, articleDao.count());
    }

    @Test
    public void testCreateEmbroidery() {
        assertEquals(4, embroideryDao.count());
    }

    @Test
    public void testCreateTextilePrinting() {
        assertEquals(4, textilePrintingDao.count());
    }
    
    @Test
    public void testFindProductByCode() {
        String code = "embroidery2";
        Product product = productDao.findFirstByCode(code);
        assertNotNull(product);
        assertEquals(code, product.getCode());
    }

}
