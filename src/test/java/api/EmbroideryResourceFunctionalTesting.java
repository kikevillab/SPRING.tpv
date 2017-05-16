package api;

import java.math.BigDecimal;
import java.util.Random;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import wrappers.EmbroideryCreationWrapper;

public class EmbroideryResourceFunctionalTesting {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void setUpOnce() {
        new RestService().seedDatabase();
    }
    
    @Test
    public void testCreateEmbroidery(){
        String token = new RestService().loginAdmin();
        EmbroideryCreationWrapper embroideryCreationWrapper = new EmbroideryCreationWrapper();
        embroideryCreationWrapper.setCode("CODE");
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
}
