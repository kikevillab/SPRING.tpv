package api;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.http.HttpStatus;

import wrappers.ArticleCreationWrapper;
import wrappers.ArticleUpdateWrapper;
import wrappers.ArticleWrapper;

public class ArticleResourceFunctionalTesting {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUpOnce() {
        new RestService().seedDatabase();
    }

    @Test
    public void testCreateArticle() {
        String token = new RestService().loginAdmin();
        ArticleCreationWrapper articleCreationWrapper = new ArticleCreationWrapper();
        articleCreationWrapper.setCode("CODE");
        articleCreationWrapper.setDescription("DESCRIPTION");
        articleCreationWrapper.setReference("REFERENCE");
        articleCreationWrapper.setDiscontinued(false);
        articleCreationWrapper.setImage("IMAGE_URL");
        articleCreationWrapper.setRetailPrice(new BigDecimal(new Random().nextDouble()));
        articleCreationWrapper.setStock(new Random().nextInt());
        articleCreationWrapper.setWholesalePrice(new BigDecimal(new Random().nextDouble()));
        new RestBuilder<Object>(RestService.URL).path(Uris.ARTICLES).body(articleCreationWrapper).basicAuth(token, "")
                .clazz(Object.class).post().build();
    }

    @Test
    public void testUpdateArticleWithNonExistentArticle() {
        thrown.expect(new HttpMatcher(HttpStatus.NOT_FOUND));
        String code = "4854546562789";
        String token = new RestService().loginAdmin();
        String desc = "test_desc";
        boolean discontinued = true;
        String image = "test_url";
        int stock = new Random().nextInt();
        BigDecimal wholesalePrice = new BigDecimal(new Random().nextDouble());
        ArticleUpdateWrapper articleUpdateWrapper = new ArticleUpdateWrapper();
        articleUpdateWrapper.setCode("CODE");
        articleUpdateWrapper.setReference("REFERENCE");
        articleUpdateWrapper.setDescription(desc);
        articleUpdateWrapper.setDiscontinued(discontinued);
        articleUpdateWrapper.setImage(image);
        articleUpdateWrapper.setRetailPrice(new BigDecimal(new Random().nextDouble()));
        articleUpdateWrapper.setStock(stock);
        articleUpdateWrapper.setWholesalePrice(wholesalePrice);
        new RestBuilder<Object>(RestService.URL).path(Uris.ARTICLES).pathId(code).body(articleUpdateWrapper)
                .basicAuth(token, "").clazz(Object.class).put().build();
    }

    @Test
    public void testUpdateArticle() {
        String code = "8400000001116";
        String token = new RestService().loginAdmin();
        boolean discontinued = true;
        String image = "test_url";
        int stock = new Random().nextInt();
        BigDecimal wholesalePrice = new BigDecimal(new Random().nextDouble());
        ArticleUpdateWrapper articleUpdateWrapper = new ArticleUpdateWrapper();
        articleUpdateWrapper.setCode(code);
        articleUpdateWrapper.setDescription("DESCRIPTION");
        articleUpdateWrapper.setReference("REFERENCE");
        articleUpdateWrapper.setDiscontinued(discontinued);
        articleUpdateWrapper.setImage(image);
        articleUpdateWrapper.setRetailPrice(new BigDecimal(new Random().nextDouble()));
        articleUpdateWrapper.setStock(stock);
        articleUpdateWrapper.setWholesalePrice(wholesalePrice);
        new RestBuilder<Object>(RestService.URL).path(Uris.ARTICLES).pathId(code).body(articleUpdateWrapper)
                .basicAuth(token, "").clazz(Object.class).put().build();
    }

    @Test
    public void testFindOneArticle() {
        String code = "8400000001114";
        String token = new RestService().loginAdmin();
        ArticleWrapper articleWrapper = new RestBuilder<ArticleWrapper>(RestService.URL)
                .path(Uris.ARTICLES).pathId(code).basicAuth(token, "").clazz(ArticleWrapper.class).get().build();
        assertNotNull(articleWrapper);
    }

    @Test
    public void testFindAllArticles() {
        String token = new RestService().loginAdmin();
        List<ArticleWrapper> articleWrappers = Arrays.asList(new RestBuilder<ArticleWrapper[]>(RestService.URL)
                .path(Uris.ARTICLES).basicAuth(token, "").clazz(ArticleWrapper[].class).get().build());
        assertNotNull(articleWrappers);
        assertFalse(articleWrappers.isEmpty());
    }

    @Test
    public void testDeleteArticle() {
        String code = "8400000001115";
        String token = new RestService().loginAdmin();
        new RestBuilder<Object>(RestService.URL).path(Uris.ARTICLES).pathId(code).basicAuth(token, "").clazz(Object.class).delete()
                .build();
    }

    @After
    public void tearDownOnce() {
        new RestService().deleteAllExceptAdmin();
    }
}
