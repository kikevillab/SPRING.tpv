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

import wrappers.TextilePrintingCreationWrapper;
import wrappers.TextilePrintingUpdateWrapper;
import wrappers.TextilePrintingWrapper;

public class TextilePrintingResourceFunctionalTesting {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUpOnce() {
        new RestService().seedDatabase();
    }

    @Test
    public void testCreateTextilePrinting() {
        String token = new RestService().loginAdmin();
        TextilePrintingCreationWrapper textilePrintingCreationWrapper = new TextilePrintingCreationWrapper();
        textilePrintingCreationWrapper.setCode("CODE");
        textilePrintingCreationWrapper.setReference("REFERENCE");
        textilePrintingCreationWrapper.setDescription("DESCRIPTION");
        textilePrintingCreationWrapper.setDiscontinued(false);
        textilePrintingCreationWrapper.setImage("IMAGE_URL");
        textilePrintingCreationWrapper.setRetailPrice(new BigDecimal(new Random().nextDouble()));
        textilePrintingCreationWrapper.setType("TYPE");
        new RestBuilder<Object>(RestService.URL).path(Uris.TEXTILE_PRINTINGS).body(textilePrintingCreationWrapper).basicAuth(token, "")
                .clazz(Object.class).post().build();
    }

    @Test
    public void testUpdateTextilePrintingWithNonExistentTextilePrinting() {
        thrown.expect(new HttpMatcher(HttpStatus.NOT_FOUND));
        String code = "00000000000";
        String token = new RestService().loginAdmin();
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
        new RestBuilder<Object>(RestService.URL).path(Uris.TEXTILE_PRINTINGS).pathId(code).body(textilePrintingUpdateWrapper)
                .basicAuth(token, "").clazz(Object.class).put().build();
    }

    @Test
    public void testUpdateTextilePrinting() {
        String code = "84000003333";
        String token = new RestService().loginAdmin();
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
        new RestBuilder<Object>(RestService.URL).path(Uris.TEXTILE_PRINTINGS).pathId(code).body(textilePrintingUpdateWrapper)
                .basicAuth(token, "").clazz(Object.class).put().build();
    }

    @Test
    public void testFindOneTextilePrinting() {
        String code = "84000003333";
        String token = new RestService().loginAdmin();
        TextilePrintingWrapper textilePrintingWrapper = new RestBuilder<TextilePrintingWrapper>(RestService.URL)
                .path(Uris.TEXTILE_PRINTINGS).pathId(code).basicAuth(token, "").clazz(TextilePrintingWrapper.class).get().build();
        assertNotNull(textilePrintingWrapper);
    }

    @Test
    public void testFindAllTextilePrintings() {
        String token = new RestService().loginAdmin();
        List<TextilePrintingWrapper> textilePrintingWrappers = Arrays.asList(new RestBuilder<TextilePrintingWrapper[]>(RestService.URL)
                .path(Uris.TEXTILE_PRINTINGS).basicAuth(token, "").clazz(TextilePrintingWrapper[].class).get().build());
        assertNotNull(textilePrintingWrappers);
        assertFalse(textilePrintingWrappers.isEmpty());
    }

    @Test
    public void testDeleteTextilePrinting() {
        String code = "84000003337";
        String token = new RestService().loginAdmin();
        new RestBuilder<Object>(RestService.URL).path(Uris.TEXTILE_PRINTINGS).pathId(code).basicAuth(token, "").clazz(Object.class).delete()
                .build();
    }

    @After
    public void tearDownOnce() {
        new RestService().deleteAll();
    }
}
