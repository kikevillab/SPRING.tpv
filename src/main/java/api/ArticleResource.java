package api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import api.exceptions.ArticleNotFoundException;
import controllers.ArticleController;
import wrappers.ArticleCreationWrapper;
import wrappers.ArticleUpdateWrapper;
import wrappers.ArticleWrapper;

@RestController
@RequestMapping(Uris.VERSION + Uris.ARTICLES)
public class ArticleResource {

    private ArticleController articleController;

    @Autowired
    public void setArticleController(ArticleController articleController) {
        this.articleController = articleController;
    }

    @RequestMapping(method = RequestMethod.POST)
    public void createArticle(@RequestBody ArticleCreationWrapper articleCreationWrapper) {
        // TODO Check fields
        articleController.createArticle(articleCreationWrapper);
    }

    //TODO ArticleUpdateWrapper codigo repetido respecto ArticleWrapper
    @RequestMapping(value = Uris.PRODUCT_CODE_ID, method = RequestMethod.PUT)
    public void updateArticle(@PathVariable String code, @RequestBody ArticleUpdateWrapper articleUpdateWrapper) throws ArticleNotFoundException {
        throwExceptionIfArticleDoesNotExist(code);
        articleController.updateArticle(articleUpdateWrapper);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<ArticleWrapper> findAllArticles() {
        return articleController.findAllArticles();
    }

    @RequestMapping(value = Uris.PRODUCT_CODE_ID, method = RequestMethod.GET)
    public ArticleWrapper findOneArticle(@PathVariable String code) throws ArticleNotFoundException {
        throwExceptionIfArticleDoesNotExist(code);
        return articleController.findOneArticle(code);
    }
    
    @RequestMapping(value = Uris.PRODUCT_CODE_ID, method = RequestMethod.DELETE)
    public void deleteArticle(@PathVariable String code) throws ArticleNotFoundException{
        throwExceptionIfArticleDoesNotExist(code);
        articleController.deleteArticle(code);        
    }
    
    private void throwExceptionIfArticleDoesNotExist(String code) throws ArticleNotFoundException{
        if(!articleController.articleExists(code)){
            throw new ArticleNotFoundException("Code: " + code);
        }
    }
}
