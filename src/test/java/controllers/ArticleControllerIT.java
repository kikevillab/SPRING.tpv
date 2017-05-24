package controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import config.PersistenceConfig;
import config.TestsControllerConfig;
import config.TestsPersistenceConfig;
import daos.core.ArticleDao;
import entities.core.Article;
import wrappers.ArticleCreationWrapper;
import wrappers.ArticleUpdateWrapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfig.class, TestsPersistenceConfig.class, TestsControllerConfig.class})
public class ArticleControllerIT {

    @Autowired
    private ArticleController articleController;

    @Autowired
    private ArticleDao articleDao;

    @Test
    public void testExistentArticleCode() {
        String articleCode = "8400000001111";
        assertTrue(articleController.articleCodeExists(articleCode));
    }

    @Test
    public void testNonexistentArticleCode() {
        String articleCode = "justTesting-123";
        assertFalse(articleController.articleCodeExists(articleCode));
    }
    
    @Test
    public void testConsumeArticle() {
        String articleCode = "8400000001111";
        int amount = 2;
        
        Article article = articleDao.findOne(articleCode);
        int previousStock = article.getStock();
        assertTrue(articleController.hasEnoughStock(articleCode, amount));
        articleController.consumeArticle(articleCode, amount);
        article = articleDao.findOne(articleCode);
        assertEquals(amount, previousStock - article.getStock());
        
        //Leave database as it was
        article.setStock(previousStock);
        articleDao.save(article);
    }
    
    @Test
    public void testArticleHasNotEnoughStock() {
        String articleCode = "article0";
        int amount = 100;
        assertFalse(articleController.hasEnoughStock(articleCode, amount));
    }
    
    @Test
    public void testCreateArticle() {
        ArticleCreationWrapper articleCreationWrapper = new ArticleCreationWrapper();
        articleCreationWrapper.setCode("CODE");
        articleCreationWrapper.setDescription("DESCRIPTION");
        articleCreationWrapper.setReference("REFERENCE");
        articleCreationWrapper.setDiscontinued(false);
        articleCreationWrapper.setImage("IMAGE_URL");
        articleCreationWrapper.setRetailPrice(new BigDecimal(new Random().nextDouble()));
        articleCreationWrapper.setStock(new Random().nextInt());
        articleCreationWrapper.setWholesalePrice(new BigDecimal(new Random().nextDouble()));
        long previousCount = articleDao.count();
        articleController.createArticle(articleCreationWrapper);
        assertEquals(previousCount + 1, articleDao.count());
        articleDao.delete("CODE");
    }

    @Test
    public void testUpdateArticle() {
        String desc = "test_desc";
        boolean discontinued = true;
        String image = "test_url";
        int stock = new Random().nextInt();
        BigDecimal wholesalePrice = new BigDecimal(new Random().nextDouble());
        Article randomArticle = articleDao.findAll().get(0);
        ArticleUpdateWrapper articleUpdateWrapper = new ArticleUpdateWrapper();
        articleUpdateWrapper.setCode(randomArticle.getCode());
        articleUpdateWrapper.setReference(randomArticle.getReference());
        articleUpdateWrapper.setDescription(desc);
        articleUpdateWrapper.setDiscontinued(discontinued);
        articleUpdateWrapper.setImage(image);
        articleUpdateWrapper.setRetailPrice(randomArticle.getRetailPrice());
        articleUpdateWrapper.setStock(stock);
        articleUpdateWrapper.setWholesalePrice(wholesalePrice);
        articleController.updateArticle(articleUpdateWrapper);
        Article sameArticle = articleDao.findOne(randomArticle.getCode());
        assertEquals(desc, sameArticle.getDescription());
        assertEquals(discontinued, sameArticle.isDiscontinued());
        assertEquals(image, sameArticle.getImage());
        assertEquals(stock, sameArticle.getStock());
        assertEquals(0.005, wholesalePrice.doubleValue(), sameArticle.getWholesalePrice().doubleValue());
    }
}
