package controllers;


import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import daos.users.TokenDao;

public class TokenTasklet implements Tasklet {
    
    private Environment environment;

    private TokenDao tokenDao;
    
    @Autowired
    public void setTokenDao(TokenDao tokenDao) {
        this.tokenDao = tokenDao;
    }
    
    @Autowired
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
    
    @Override
    public RepeatStatus execute(StepContribution arg0, ChunkContext arg1) throws Exception {
        LogManager.getLogger().info("++++ Ejecutando task de limpieza de tokens ++++");
        //tokenDao.deleteByCreationDateLessThan(new Date(new Date().getTime() - Integer.parseInt(environment.getProperty("tokenTime.user"))));
        return RepeatStatus.FINISHED;
    }

}
