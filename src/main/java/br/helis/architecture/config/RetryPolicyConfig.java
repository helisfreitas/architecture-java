package br.helis.architecture.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;

import br.helis.architecture.notifications.service.CustomRetryListener;

@Configuration
@EnableRetry
public class RetryPolicyConfig {

    @Bean
    public CustomRetryListener customRetryListener() {
        return new CustomRetryListener();
    }    
}
