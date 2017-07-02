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
import wrappers.TextilePrintingCreationWrapper;
import wrappers.TextilePrintingUpdateWrapper;
import wrappers.TextilePrintingWrapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestsApiConfig.class})
public class TextilePrintingResourceFunctionalTesting {

    @Autowired
    private RestService restService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void none() {

    }

    // TODO Replantearse los test con herencia, ahora resultan muy repetitivos
    // @Test
    public void testCreateTextilePrinting() {
        TextilePrintingCreationWrapper textilePrintingCreationWrapper = new TextilePrintingCreationWrapper();
        textilePrintingCreationWrapper.setCode("CODE");
        textilePrintingCreationWrapper.setReference("REFERENCE");
        textilePrintingCreationWrapper.setDescription("DESCRIPTION");
        textilePrintingCreationWrapper.setDiscontinued(false);
        textilePrintingCreationWrapper.setImage("IMAGE_URL");
        textilePrintingCreationWrapper.setRetailPrice(new BigDecimal(new Random().nextDouble()));
        textilePrintingCreationWrapper.setType("TYPE");
        new RestBuilder<Object>(restService.getUrl()).path(Uris.TEXTILE_PRINTINGS).body(textilePrintingCreationWrapper)
                .basicAuth(restService.loginAdmin(), "").clazz(Object.class).post().build();
    }

    // @Test
    public void testUpdateTextilePrintingWithNonExistentTextilePrinting() {
        thrown.expect(new HttpMatcher(HttpStatus.NOT_FOUND));
        String code = "00000000000";
        String desc = "test_desc";
        boolean discontinued = true;
        String image = "test_url";
        String type = "test_type";
        TextilePrintingUpdateWrapper textilePrintingUpdateWrapper = new TextilePrintingUpdateWrapper();
        textilePrintingUpdateWrapper.setCode("CODE");
        textilePrintingUpdateWrapper.setReference("REFERENCE");
        textilePrintingUpdateWrapper.setDescription(desc);
        textilePrintingUpdateWrapper.setDiscontinued(discontinued);
        textilePrintingUpdateWrapper.setImage(image);
        textilePrintingUpdateWrapper.setRetailPrice(new BigDecimal(new Random().nextDouble()));
        textilePrintingUpdateWrapper.setType(type);
        new RestBuilder<Object>(restService.getUrl()).path(Uris.TEXTILE_PRINTINGS).pathId(code).body(textilePrintingUpdateWrapper)
                .basicAuth(restService.loginAdmin(), "").clazz(Object.class).put().build();
    }

    // @Test
    public void testUpdateTextilePrinting() {
        String code = "8400000003333";
        String desc = "test_desc";
        boolean discontinued = true;
        String image = "test_url";
        String type = "test_type";
        TextilePrintingUpdateWrapper textilePrintingUpdateWrapper = new TextilePrintingUpdateWrapper();
        textilePrintingUpdateWrapper.setCode("CODE");
        textilePrintingUpdateWrapper.setReference("REFERENCE");
        textilePrintingUpdateWrapper.setDescription(desc);
        textilePrintingUpdateWrapper.setDiscontinued(discontinued);
        textilePrintingUpdateWrapper.setImage(image);
        textilePrintingUpdateWrapper.setRetailPrice(new BigDecimal(new Random().nextDouble()));
        textilePrintingUpdateWrapper.setType(type);
        new RestBuilder<Object>(restService.getUrl()).path(Uris.TEXTILE_PRINTINGS).pathId(code).body(textilePrintingUpdateWrapper)
                .basicAuth(restService.loginAdmin(), "").clazz(Object.class).put().build();
    }

    // @Test
    public void testFindOneTextilePrinting() {
        String code = "8400000003333";
        TextilePrintingWrapper textilePrintingWrapper = new RestBuilder<TextilePrintingWrapper>(restService.getUrl())
                .path(Uris.TEXTILE_PRINTINGS).pathId(code).basicAuth(restService.loginAdmin(), "").clazz(TextilePrintingWrapper.class).get()
                .build();
        assertNotNull(textilePrintingWrapper);
    }

    // @Test
    public void testFindAllTextilePrintings() {
        List<TextilePrintingWrapper> textilePrintingWrappers = Arrays.asList(new RestBuilder<TextilePrintingWrapper[]>(restService.getUrl())
                .path(Uris.TEXTILE_PRINTINGS).basicAuth(restService.loginAdmin(), "").clazz(TextilePrintingWrapper[].class).get().build());
        assertNotNull(textilePrintingWrappers);
        assertFalse(textilePrintingWrappers.isEmpty());
    }

    // @Test
    public void testDeleteTextilePrinting() {
        String code = "8400000003337";
        new RestBuilder<Object>(restService.getUrl()).path(Uris.TEXTILE_PRINTINGS).pathId(code).basicAuth(restService.loginAdmin(), "")
                .clazz(Object.class).delete().build();
    }

}
