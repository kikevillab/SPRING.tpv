package api;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import config.TestsApiConfig;
import wrappers.ArticleCreationWrapper;
import wrappers.ArticleUpdateWrapper;
import wrappers.ArticleWrapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestsApiConfig.class})
public class ArticleResourceFunctionalTesting {

    @Autowired
    private RestService restService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testCreateDelete() {
        ArticleCreationWrapper articleCreationWrapper = new ArticleCreationWrapper();
        articleCreationWrapper.setCode("code");
        articleCreationWrapper.setDescription("description");
        articleCreationWrapper.setReference("reference");
        articleCreationWrapper.setDiscontinued(false);
        articleCreationWrapper.setImage("imageUrl");
        articleCreationWrapper.setRetailPrice(new BigDecimal(new Random().nextDouble()));
        articleCreationWrapper.setStock(new Random().nextInt());
        articleCreationWrapper.setWholesalePrice(new BigDecimal(new Random().nextDouble()));
        new RestBuilder<Object>(restService.getUrl()).path(Uris.ARTICLES).body(articleCreationWrapper)
                .basicAuth(restService.loginAdmin(), "").clazz(Object.class).post().build();
        new RestBuilder<>(restService.getUrl()).path(Uris.ARTICLES).path("/code").basicAuth(restService.loginAdmin(), "").delete().build();
    }

    @Test
    public void testUpdateArticleWithNonExistentArticle() {
        thrown.expect(new HttpMatcher(HttpStatus.NOT_FOUND));
        ArticleUpdateWrapper articleUpdateWrapper = new ArticleUpdateWrapper();
        articleUpdateWrapper.setCode("code");
        articleUpdateWrapper.setReference("reference");
        articleUpdateWrapper.setDescription("description");
        articleUpdateWrapper.setDiscontinued(true);
        articleUpdateWrapper.setImage("imageUrl");
        articleUpdateWrapper.setRetailPrice(new BigDecimal(23.1));
        articleUpdateWrapper.setStock(34);
        articleUpdateWrapper.setWholesalePrice(new BigDecimal(11.1));
        new RestBuilder<Object>(restService.getUrl()).path(Uris.ARTICLES).pathId("notCode").body(articleUpdateWrapper)
                .basicAuth(restService.loginAdmin(), "").clazz(Object.class).put().build();
    }

    @Test
    public void testFindOneArticle() {
        ArticleWrapper articleWrapper = new RestBuilder<ArticleWrapper>(restService.getUrl()).path(Uris.ARTICLES).pathId("8400000001114")
                .basicAuth(restService.loginAdmin(), "").clazz(ArticleWrapper.class).get().build();
        assertNotNull(articleWrapper);
    }

    @Test
    public void testUpdateArticle() {
        ArticleWrapper articleWrapper = new RestBuilder<ArticleWrapper>(restService.getUrl()).path(Uris.ARTICLES).pathId("8400000001116")
                .basicAuth(restService.loginAdmin(), "").clazz(ArticleWrapper.class).get().build();
        ArticleUpdateWrapper articleUpdateWrapper = new ArticleUpdateWrapper();
        articleUpdateWrapper.setCode(articleWrapper.getCode());
        articleUpdateWrapper.setDescription("DESCRIPTION");
        articleUpdateWrapper.setReference("REFERENCE");
        articleUpdateWrapper.setDiscontinued(true);
        articleUpdateWrapper.setImage("imageUrl");
        articleUpdateWrapper.setRetailPrice(new BigDecimal(23.4));
        articleUpdateWrapper.setStock(56);
        articleUpdateWrapper.setWholesalePrice(new BigDecimal(11.4));
        new RestBuilder<Object>(restService.getUrl()).path(Uris.ARTICLES).pathId("8400000001116").body(articleUpdateWrapper)
        .basicAuth(restService.loginAdmin(), "").clazz(Object.class).put().build();
        articleUpdateWrapper.setDescription(articleWrapper.getDescription());
        articleUpdateWrapper.setReference(articleWrapper.getReference());
        articleUpdateWrapper.setDiscontinued(articleWrapper.isDiscontinued());
        articleUpdateWrapper.setImage(articleWrapper.getImage());
        articleUpdateWrapper.setRetailPrice(articleWrapper.getRetailPrice());
        articleUpdateWrapper.setStock(articleWrapper.getStock());
        articleUpdateWrapper.setWholesalePrice(articleWrapper.getWholesalePrice());
        new RestBuilder<Object>(restService.getUrl()).path(Uris.ARTICLES).pathId("8400000001116").body(articleUpdateWrapper)
        .basicAuth(restService.loginAdmin(), "").clazz(Object.class).put().build();
    }

    @Test
    public void testFindAllArticles() {
        List<ArticleWrapper> articleWrappers = Arrays.asList(new RestBuilder<ArticleWrapper[]>(restService.getUrl()).path(Uris.ARTICLES)
                .basicAuth(restService.loginAdmin(), "").clazz(ArticleWrapper[].class).get().build());
        assertNotNull(articleWrappers);
        assertFalse(articleWrappers.isEmpty());
    }

}
