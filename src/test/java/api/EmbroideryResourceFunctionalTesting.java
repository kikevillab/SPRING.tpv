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
import wrappers.EmbroideryCreationWrapper;
import wrappers.EmbroideryUpdateWrapper;
import wrappers.EmbroideryWrapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestsApiConfig.class})
public class EmbroideryResourceFunctionalTesting {

    @Autowired
    private RestService restService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void none() {

    }

    // TODO Replantearse los test con herencia, ahora resultan muy repetitivos
    // @Test
    public void testCreateEmbroidery() {
        EmbroideryCreationWrapper embroideryCreationWrapper = new EmbroideryCreationWrapper();
        embroideryCreationWrapper.setCode("CODE");
        embroideryCreationWrapper.setReference("REFERENCE");
        embroideryCreationWrapper.setColors(new Random().nextInt());
        embroideryCreationWrapper.setDescription("DESCRIPTION");
        embroideryCreationWrapper.setDiscontinued(false);
        embroideryCreationWrapper.setImage("IMAGE_URL");
        embroideryCreationWrapper.setRetailPrice(new BigDecimal(new Random().nextDouble()));
        embroideryCreationWrapper.setSquareMillimeters(new Random().nextInt());
        embroideryCreationWrapper.setStitches(new Random().nextInt());
        new RestBuilder<Object>(restService.getUrl()).path(Uris.EMBROIDERIES).body(embroideryCreationWrapper)
                .basicAuth(restService.loginAdmin(), "").clazz(Object.class).post().build();
    }

    // @Test
    public void testUpdateEmbroideryWithNonExistentEmbroidery() {
        thrown.expect(new HttpMatcher(HttpStatus.NOT_FOUND));
        String code = "0000000000000";
        EmbroideryUpdateWrapper embroideryUpdateWrapper = new EmbroideryUpdateWrapper();
        embroideryUpdateWrapper.setCode("CODE");
        embroideryUpdateWrapper.setReference("REFERENCE");
        embroideryUpdateWrapper.setColors(new Random().nextInt());
        embroideryUpdateWrapper.setDescription("DESCRIPTION");
        embroideryUpdateWrapper.setDiscontinued(false);
        embroideryUpdateWrapper.setImage("IMAGE_URL");
        embroideryUpdateWrapper.setRetailPrice(new BigDecimal(new Random().nextDouble()));
        embroideryUpdateWrapper.setSquareMillimeters(new Random().nextInt());
        embroideryUpdateWrapper.setStitches(new Random().nextInt());
        new RestBuilder<Object>(restService.getUrl()).path(Uris.EMBROIDERIES).pathId(code).body(embroideryUpdateWrapper)
                .basicAuth(restService.loginAdmin(), "").clazz(Object.class).put().build();
    }

    // @Test
    public void testUpdateEmbroidery() {
        String code = "8400000002222";
        EmbroideryUpdateWrapper embroideryUpdateWrapper = new EmbroideryUpdateWrapper();
        embroideryUpdateWrapper.setCode("CODE");
        embroideryUpdateWrapper.setReference("REFERENCE");
        embroideryUpdateWrapper.setColors(new Random().nextInt());
        embroideryUpdateWrapper.setDescription("DESCRIPTION");
        embroideryUpdateWrapper.setDiscontinued(false);
        embroideryUpdateWrapper.setImage("IMAGE_URL");
        embroideryUpdateWrapper.setRetailPrice(new BigDecimal(new Random().nextDouble()));
        embroideryUpdateWrapper.setSquareMillimeters(new Random().nextInt());
        embroideryUpdateWrapper.setStitches(new Random().nextInt());
        new RestBuilder<Object>(restService.getUrl()).path(Uris.EMBROIDERIES).pathId(code).body(embroideryUpdateWrapper)
                .basicAuth(restService.loginAdmin(), "").clazz(Object.class).put().build();
    }

    // @Test
    public void testFindOneEmbroidery() {
        String code = "8400000002222";
        EmbroideryWrapper embroideryWrapper = new RestBuilder<EmbroideryWrapper>(restService.getUrl()).path(Uris.EMBROIDERIES).pathId(code)
                .basicAuth(restService.loginAdmin(), "").clazz(EmbroideryWrapper.class).get().build();
        assertNotNull(embroideryWrapper);
    }

    // @Test
    public void testFindAllEmbroideries() {
        List<EmbroideryWrapper> embroideryWrappers = Arrays.asList(new RestBuilder<EmbroideryWrapper[]>(restService.getUrl())
                .path(Uris.EMBROIDERIES).basicAuth(restService.loginAdmin(), "").clazz(EmbroideryWrapper[].class).get().build());
        assertNotNull(embroideryWrappers);
        assertFalse(embroideryWrappers.isEmpty());
    }

    // @Test
    public void testDeleteEmbroidery() {
        String code = "8400000002226";
        new RestBuilder<Object>(restService.getUrl()).path(Uris.EMBROIDERIES).pathId(code).basicAuth(restService.loginAdmin(), "")
                .clazz(Object.class).delete().build();
    }

}
