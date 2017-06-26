package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import api.RestService;

@Configuration
public class TestsApiConfig {
    
    @Bean
    public RestService restService() {
        return new RestService();
    }

}
