package batch;

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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ServiceBatch {

    @Autowired
    Job job;

    @Autowired
    JobLauncher jobLauncher;

    @Scheduled(cron = CronConstans.EJECUCION_CADA_MINUTO)
    public void launch() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException,
            JobParametersInvalidException {

        JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis()).toJobParameters();
        JobExecution execution = jobLauncher.run(job, jobParameters);
        LogManager.getLogger().info("Exit status: " + execution.getStatus() + " ");
    }

}
