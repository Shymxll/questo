package com.project.questo.request;

import java.util.ArrayList;
import java.util.List;

import com.project.questo.entity.Message;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class AIRequest {
    private String model;
    private List<Message> messages;
    private int n = 1;
    private double temperature;

    public AIRequest(String model, String prompt) {
        this.model = model;

        this.messages = new ArrayList<>();
        this.messages.add(new Message("user", prompt));
       }
}
