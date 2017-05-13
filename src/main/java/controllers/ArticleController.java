package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import daos.core.ArticleDao;
import entities.core.Article;

@Controller
public class ArticleController {

    private ArticleDao articleDao;

    @Autowired
    public void setArticleDao(ArticleDao productDao) {
        this.articleDao = productDao;
    }

    public boolean articleCodeExists(String articleCode) {
        Article article = articleDao.findFirstByCode(articleCode);
        return article != null;
    }
    
    public Article getArticleByCode(String articleCode) {
        return articleDao.findFirstByCode(articleCode);
    }

    public boolean hasEnoughStock(String articleCode, int amount) {
        Article article = articleDao.findFirstByCode(articleCode);
        boolean enoughStock = false;
        if (article != null) {
            if (article.getStock() >= amount) {
                enoughStock = true;
            }
        }
        return enoughStock;
    }

    public void consumeArticle(String articleCode, int amount) {
        Article article = articleDao.findFirstByCode(articleCode);
        if (article != null) {
            int stock = article.getStock();
            article.setStock(stock - amount);
            articleDao.save(article);
        }
    }
}
