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
import daos.core.TextilePrintingDao;
import entities.core.TextilePrinting;
import wrappers.TextilePrintingCreationWrapper;
import wrappers.TextilePrintingUpdateWrapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfig.class, TestsPersistenceConfig.class, TestsControllerConfig.class})
public class TextilPrintingControllerIT {

    @Autowired
    private TextilePrintingController textilePrintingController;

    @Autowired
    private TextilePrintingDao textilePrintingDao;

    @Test
    public void testCreateTextilePrinting() {
        TextilePrintingCreationWrapper textilePrintingCreationWrapper = new TextilePrintingCreationWrapper();
        textilePrintingCreationWrapper.setCode("CODE");
        textilePrintingCreationWrapper.setDescription("DESCRIPTION");
        textilePrintingCreationWrapper.setDiscontinued(false);
        textilePrintingCreationWrapper.setImage("IMAGE_URL");
        textilePrintingCreationWrapper.setRetailPrice(new BigDecimal(new Random().nextDouble()));
        textilePrintingCreationWrapper.setType("TYPE");
        long previousCount = textilePrintingDao.count();
        textilePrintingController.createTextilePrinting(textilePrintingCreationWrapper);
        assertEquals(previousCount + 1, textilePrintingDao.count());
        textilePrintingDao.delete("CODE");
    }

    @Test
    public void testUpdateTextilePrinting() {
        String desc = "test_desc";
        boolean discontinued = true;
        String image = "test_url";
        String type = "test_type";
        TextilePrinting randomTextilePrinting = textilePrintingDao.findAll().get(0);
        TextilePrintingUpdateWrapper textilePrintingUpdateWrapper = new TextilePrintingUpdateWrapper();
        textilePrintingUpdateWrapper.setCode(randomTextilePrinting.getCode());
        textilePrintingUpdateWrapper.setDescription(desc);
        textilePrintingUpdateWrapper.setDiscontinued(discontinued);
        textilePrintingUpdateWrapper.setImage(image);
        textilePrintingUpdateWrapper.setRetailPrice(randomTextilePrinting.getRetailPrice());
        textilePrintingUpdateWrapper.setType(type);
        textilePrintingController.updateTextilePrinting(randomTextilePrinting.getCode(), textilePrintingUpdateWrapper);
        TextilePrinting sameTextilePrinting = textilePrintingDao.findOne(randomTextilePrinting.getCode());
        assertEquals(desc, sameTextilePrinting.getDescription());
        assertEquals(discontinued, sameTextilePrinting.isDiscontinued());
        assertEquals(image, sameTextilePrinting.getImage());
        assertEquals(type, sameTextilePrinting.getType());     
    }
}
