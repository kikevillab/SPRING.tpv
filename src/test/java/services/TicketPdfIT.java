package services;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import config.PersistenceConfig;
import config.TestsPersistenceConfig;
import daos.core.TicketDao;
import utils.pdfs.TicketPdf;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfig.class, TestsPersistenceConfig.class})
public class TicketPdfIT {

    @Autowired
    private TicketDao ticketDao;

    @Test
    public void testGenerate() {
        new TicketPdf().generate(ticketDao.findAll().get(0));
    }

}
