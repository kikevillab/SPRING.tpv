package batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 *  Componentes principales de Spring Batch:
    JobRepository: es el componente encargado de la persistencia de metadatos relativos a los procesos tales como procesos en curso o estados de las ejecuciones.
    JobLauncher: es el componente encargado de lanzar los procesos suministrando los parámetros de entrada deseados.
    Job: El Job es la representación del proceso. Un proceso, a su vez, es un contenedor de pasos (steps).
    Step: Un step (paso) es un elemento independiente dentro de un Job (un proceso) que representa una de las fases de las que está compuesto dicho proceso.
    Un proceso (Job) debe tener, al menos, un step.
 *   
 */
@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Step stepCleanTokens() {
        return stepBuilderFactory.get("stepCleanTokens").tasklet(tasklet()).allowStartIfComplete(true).build();
    }

    @Bean
    public Job job(Step stepCleanTokens) throws Exception {
        return jobBuilderFactory.get("jobToken").incrementer(new RunIdIncrementer()).start(stepCleanTokens).build();
    }

    @Bean
    public Tasklet tasklet() {
        // Metodo necesario para que Spring reconozca el NEW TokenControllerTasklet
        return new TokenBatchTasklet();
    }

}
