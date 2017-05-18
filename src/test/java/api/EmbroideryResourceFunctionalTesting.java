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

import wrappers.EmbroideryCreationWrapper;
import wrappers.EmbroideryUpdateWrapper;
import wrappers.EmbroideryWrapper;

public class EmbroideryResourceFunctionalTesting {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUpOnce() {
        new RestService().seedDatabase();
    }
    
    @Test
    public void testCreateEmbroidery(){
        String token = new RestService().loginAdmin();
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
        new RestBuilder<Object>(RestService.URL).path(Uris.EMBROIDERIES).body(embroideryCreationWrapper).basicAuth(token, "")
        .clazz(Object.class).post().build();
    }
    
    @Test
    public void testUpdateEmbroideryWithNonExistentEmbroidery(){
        thrown.expect(new HttpMatcher(HttpStatus.NOT_FOUND));
        String code = "00000000000";
        String token = new RestService().loginAdmin();
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
        new RestBuilder<Object>(RestService.URL).path(Uris.EMBROIDERIES).pathId(code).body(embroideryUpdateWrapper).basicAuth(token, "")
        .clazz(Object.class).put().build();
    }
    
    @Test
    public void testUpdateEmbroidery(){
        String code = "84000002222";
        String token = new RestService().loginAdmin();
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
        new RestBuilder<Object>(RestService.URL).path(Uris.EMBROIDERIES).pathId(code).body(embroideryUpdateWrapper).basicAuth(token, "")
        .clazz(Object.class).put().build();
    }
    
    @Test
    public void testFindOneEmbroidery(){
        String code = "84000002222";
        String token = new RestService().loginAdmin();
        EmbroideryWrapper embroideryWrapper = new RestBuilder<EmbroideryWrapper>(RestService.URL).path(Uris.EMBROIDERIES).pathId(code).basicAuth(token, "")
        .clazz(EmbroideryWrapper.class).get().build();
        assertNotNull(embroideryWrapper);
    }
    
    @Test
    public void testFindAllEmbroideries(){
        String token = new RestService().loginAdmin();
        List<EmbroideryWrapper> embroideryWrappers = Arrays.asList(new RestBuilder<EmbroideryWrapper[]>(RestService.URL).path(Uris.EMBROIDERIES).basicAuth(token, "")
        .clazz(EmbroideryWrapper[].class).get().build());
        assertNotNull(embroideryWrappers);
        assertFalse(embroideryWrappers.isEmpty());
    }
    
    @Test
    public void testDeleteEmbroidery(){
        String code = "84000002226";
        String token = new RestService().loginAdmin();
        new RestBuilder<Object>(RestService.URL).path(Uris.EMBROIDERIES).pathId(code).basicAuth(token, "")
        .clazz(Object.class).delete().build();
    }
    
    @After
    public void tearDownOnce(){
        new RestService().deleteAll();
    }
}
        