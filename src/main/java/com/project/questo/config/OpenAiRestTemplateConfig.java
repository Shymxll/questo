package com.project.questo.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class OpenAiRestTemplateConfig {

    private final String OPENAI_API_KEY = "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";

    @Bean
    @Qualifier("openaiRestTemplate")
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().add("Authorization", "Bearer " + this.OPENAI_API_KEY);
            return execution.execute(request, body);
        });
        return restTemplate;
    }
}
