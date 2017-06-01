package api;

import org.apache.logging.log4j.LogManager;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableScheduling
@RequestMapping(Uris.VERSION)
public class BacthResource {
  
    /*
      La expresión de Cron está representada por seis campos:
    
      Segundo, Minuto, Hora, Día del Mes, Mes, Día (s) de la semana
                 (*) Significa que coincide con cualquier valor
                 * / X significa "cada X"
                 ? ("Sin valor específico")
     */
    private static final String CRON_BATCH_ELIMINACION_TOKENS_CADUCADOS = "0 1 * * * ?";

    @Autowired
    Job job;

    @Autowired
    JobLauncher jobLauncher;
    
    //@Scheduled(cron="*/10 * * * * ?")
    @Scheduled(cron = CRON_BATCH_ELIMINACION_TOKENS_CADUCADOS)
    @RequestMapping(value = Uris.BATCH)
    public void launch() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
        JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis()).toJobParameters();
        JobExecution execution = jobLauncher.run(job, jobParameters);
        LogManager.getLogger().info("++++ Exit status: "+ execution.getStatus() + "++++ ");    
    }
}
    
