package com.project.questo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.project.questo.request.AIRequest;
import com.project.questo.response.AIResponse;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AIService {

    @Qualifier("openaiRestTemplate")
    @Autowired
    private RestTemplate restTemplate;

    private String model = "gpt-3.5-turbo";

    private String apiUrl = "https://api.openai.com/v1/chat/completions";

    public String getChat(String prompt) {
        try {
            AIRequest request = new AIRequest(this.model, prompt);
            // call the API
            AIResponse response = this.restTemplate.postForObject(this.apiUrl, request, AIResponse.class);

            if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
                return null;
            }

            // return the first response
            return response.getChoices().get(0).getMessage().getContent();
        } catch (Exception e) {
            return null;
        }
    }

}
