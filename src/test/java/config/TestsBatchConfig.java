package config;

import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class TestsBatchConfig {
    
  @Bean
  public JobLauncherTestUtils jobLauncherTestUtils() {
      return new JobLauncherTestUtils();
  }

}