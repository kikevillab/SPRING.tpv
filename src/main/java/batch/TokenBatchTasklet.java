package batch;

import org.apache.logging.log4j.LogManager;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

import services.RemoveTokenExpiredService;

public class TokenBatchTasklet implements Tasklet {

    private RemoveTokenExpiredService removeTokenExpiredService;

    @Autowired
    public void setRemoveTokenExpiredService(RemoveTokenExpiredService removeTokenExpiredService) {
        this.removeTokenExpiredService = removeTokenExpiredService;
    }


    
    @Override
    public RepeatStatus execute(StepContribution arg0, ChunkContext arg1) throws Exception {
        LogManager.getLogger().info("Ejecutando task de limpieza de tokens");
        removeTokenExpiredService.removeTokenExpired();
        return RepeatStatus.FINISHED;
    }

}
