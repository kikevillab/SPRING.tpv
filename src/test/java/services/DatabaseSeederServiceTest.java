package services;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;

import entities.core.Article;

public class DatabaseSeederServiceTest {

    @Test
    public void testExpandArticle() {
        Article article = new Article("8400001", "ReferenceT[2..16]", new BigDecimal(10), "Article Size T", new BigDecimal(10), null);
        article.setImage("Image");
        article.setStock(100);
        List<Article> articles = new DatabaseSeederService().expandArticle(article);
        assertEquals(8, articles.size());
        assertEquals("ReferenceT2", articles.get(0).getReference());
        assertEquals("Article Size T2", articles.get(0).getDescription());
    }
    
}
