package services;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import config.BatchConfig;
import config.PersistenceConfig;
import config.TestsBatchConfig;
import daos.users.TokenDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PersistenceConfig.class, BatchConfig.class, TestsBatchConfig.class })
public class ServiceBatchTesting {
    
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private TokenDao tokenDao;
    
    @Test
    public void launchJob() throws Exception {
        int antes = (int) tokenDao.count();
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();
        assertEquals("COMPLETED", jobExecution.getExitStatus().getExitCode());
        assertEquals(antes, tokenDao.count());
    }

}