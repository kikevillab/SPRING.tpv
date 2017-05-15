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
@RequestMapping(Uris.VERSION + Uris.EMBROIDERIES)
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

    @RequestMapping(value = Uris.ID, method = RequestMethod.PUT)
    public void updateArticle(@PathVariable long id, @RequestBody ArticleUpdateWrapper articleUpdateWrapper) throws ArticleNotFoundException {
        if (!articleController.articleExists(id)) {
            throw new ArticleNotFoundException("Id: " + id);
        }
        articleController.updateArticle(articleUpdateWrapper);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<ArticleWrapper> findAllArticles() {
        return articleController.findAllArticles();
    }

    @RequestMapping(value = Uris.ID, method = RequestMethod.GET)
    public ArticleWrapper findOneArticle(@PathVariable long id) throws ArticleNotFoundException {
        if (!articleController.articleExists(id)) {
            throw new ArticleNotFoundException("Id: " + id);
        }
        return articleController.findOneArticle(id);
    }
    
    @RequestMapping(value = Uris.ID, method = RequestMethod.DELETE)
    public void deleteArticle(@PathVariable long id) throws ArticleNotFoundException{
        if(!articleController.articleExists(id)){
            throw new ArticleNotFoundException("Id: " + id);
        }
        articleController.deleteArticle(id);        
    }
}
