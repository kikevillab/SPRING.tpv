package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import daos.core.ArticleDao;
import entities.core.Article;

@Controller
public class ArticleController {

    private ArticleDao articleDao;

    @Autowired
    public void setProductDao(ArticleDao productDao) {
        this.articleDao = productDao;
    }

    public boolean articleCodeExists(String articleCode) {
        Article article = articleDao.findFirstByCode(articleCode);
        return article != null;
    }

    public boolean consumeArticle(String articleCode, int amount) {
        Article article = articleDao.findFirstByCode(articleCode);
        boolean consumed = false;
        if (article != null) {
            int stock = article.getStock();
            if(stock >= amount) {
                article.setStock(stock - amount);
                articleDao.save(article);
                consumed = true;
            }
        }
        return consumed;
    }
}
