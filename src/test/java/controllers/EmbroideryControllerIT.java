package controllers;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import config.PersistenceConfig;
import config.TestsControllerConfig;
import config.TestsPersistenceConfig;
import daos.core.EmbroideryDao;
import entities.core.Embroidery;
import wrappers.EmbroideryCreationWrapper;
import wrappers.EmbroideryUpdateWrapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfig.class, TestsPersistenceConfig.class, TestsControllerConfig.class})
public class EmbroideryControllerIT {

    @Autowired
    private EmbroideryController embroideryController;

    @Autowired
    private EmbroideryDao embroideryDao;

    @Test
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
        long previousCount = embroideryDao.count();
        embroideryController.createEmbroidery(embroideryCreationWrapper);
        assertEquals(previousCount + 1, embroideryDao.count());
        embroideryDao.delete("CODE");
    }

    @Test
    public void testUpdateEmbroidery() {
        String desc = "test_desc";
        boolean discontinued = true;
        String image = "test_url";
        int squareMillimeters = new Random().nextInt();
        int stitches = new Random().nextInt();
        Embroidery randomEmbroidery = embroideryDao.findAll().get(0);
        EmbroideryUpdateWrapper embroideryUpdateWrapper = new EmbroideryUpdateWrapper();
        embroideryUpdateWrapper.setCode(randomEmbroidery.getCode());
        embroideryUpdateWrapper.setReference(randomEmbroidery.getReference());
        embroideryUpdateWrapper.setColors(randomEmbroidery.getColors());
        embroideryUpdateWrapper.setDescription(desc);
        embroideryUpdateWrapper.setDiscontinued(discontinued);
        embroideryUpdateWrapper.setImage(image);
        embroideryUpdateWrapper.setRetailPrice(randomEmbroidery.getRetailPrice());
        embroideryUpdateWrapper.setSquareMillimeters(squareMillimeters);
        embroideryUpdateWrapper.setStitches(stitches);
        embroideryController.updateEmbroidery(randomEmbroidery.getCode(), embroideryUpdateWrapper);
        Embroidery sameEmbroidery = embroideryDao.findOne(randomEmbroidery.getCode());
        assertEquals(desc, sameEmbroidery.getDescription());
        assertEquals(discontinued, sameEmbroidery.isDiscontinued());
        assertEquals(image, sameEmbroidery.getImage());
        assertEquals(squareMillimeters, sameEmbroidery.getSquareMillimeters());
        assertEquals(stitches, sameEmbroidery.getStitches());
    }
}
