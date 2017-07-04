package services;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;

import entities.core.Article;
import entities.core.CategoryComposite;
import entities.core.CategoryProduct;

public class DatabaseSeederServiceTest {

    @Test
    public void testExpandArticleNumeric() {
        Article article = new Article("8400001", "ReferenceT[2..16]", new BigDecimal(10), "Article Size T", new BigDecimal(10), null);
        article.setImage("Image");
        article.setStock(100);
        List<Article> articles = new DatabaseSeederService().expandArticle(article);
        assertEquals(8, articles.size());
        assertEquals("8400001020009", articles.get(0).getCode());
        assertEquals("ReferenceT2", articles.get(0).getReference());
        assertEquals("Article Size T2", articles.get(0).getDescription());
    }

    @Test
    public void testExpandArticleAlpha() {
        Article article = new Article("8400001", "ReferenceT[M..L]", new BigDecimal(10), "Article Size T", new BigDecimal(10), null);
        article.setImage("Image");
        article.setStock(100);
        List<Article> articles = new DatabaseSeederService().expandArticle(article);
        assertEquals(2, articles.size());
        assertEquals("8400001020009", articles.get(0).getCode());
        assertEquals("ReferenceTM", articles.get(0).getReference());
        assertEquals("Article Size TM", articles.get(0).getDescription());
    }

    @Test
    public void testExpandSize() {
        TpvGraph tpvGraph = new TpvGraph();
        CategoryComposite root = new CategoryComposite("category_root");
        CategoryComposite category1 = new CategoryComposite("category1");
        CategoryComposite category2 = new CategoryComposite("category2");
        root.addComponent(category1);
        root.addComponent(category2);
        Article article1 = new Article("8400001", "Reference1T[2..16]", new BigDecimal(10), "Article1 Size T", new BigDecimal(10), null);
        Article article2 = new Article("8400002", "Reference2T[2..16]", new BigDecimal(10), "Article2 Size T", new BigDecimal(10), null);
        CategoryProduct productCategory1 = new CategoryProduct(article1);
        CategoryProduct productCategory2 = new CategoryProduct(article2);
        category1.addComponent(productCategory1);
        category1.addComponent(productCategory2);
        category2.addComponent(productCategory2);
        tpvGraph.getCategoryCompositeList().add(root);
        tpvGraph.getCategoryCompositeList().add(category1);
        tpvGraph.getCategoryCompositeList().add(category2);
        tpvGraph.getProductCategoryList().add(productCategory1);
        tpvGraph.getProductCategoryList().add(productCategory2);
        tpvGraph.getArticleList().add(article1);
        tpvGraph.getArticleList().add(article2);
        new DatabaseSeederService().expandAllSizes(tpvGraph);

        assertEquals(16, category1.components().size());
        assertEquals(8, category2.components().size());
        assertEquals(16, tpvGraph.getProductCategoryList().size());
        assertEquals(16, tpvGraph.getArticleList().size());
    }

}
