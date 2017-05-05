package controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfig.class, TestsPersistenceConfig.class, TestsControllerConfig.class})
public class ArticleControllerIT {

    @Autowired
    private ArticleController articleController;

    @Autowired
    private ArticleDao articleDao;

    @Test
    public void testExistentArticleCode() {
        String articleCode = "article0";
        assertTrue(articleController.articleCodeExists(articleCode));
    }

    @Test
    public void testNonexistentArticleCode() {
        String articleCode = "justTesting-123";
        assertFalse(articleController.articleCodeExists(articleCode));
    }
    
    @Test
    public void testConsumeArticle() {
        String articleCode = "article0";
        int amount = 2;
        
        Article article = articleDao.findFirstByCode(articleCode);
        int previousStock = article.getStock();
        assertTrue(articleController.consumeArticle(articleCode, amount));
        article = articleDao.findFirstByCode(articleCode);
        assertEquals(amount, previousStock - article.getStock());
        
        //Leave database as it was
        article.setStock(previousStock);
        articleDao.save(article);
    }
    
    @Test
    public void testConsumeArticleNotEnoughStock() {
        String articleCode = "article0";
        int amount = 100;
        assertFalse(articleController.consumeArticle(articleCode, amount));
    }
}
