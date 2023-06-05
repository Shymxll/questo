package com.project.questo.controller;

import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.project.questo.entity.MyForm;
import com.project.questo.service.AIService;
import com.project.questo.service.ImageService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class GenericController {

    private ImageService imageService;
    private AIService aiService;

    public GenericController(ImageService imageService, AIService aiService) {
        this.imageService = imageService;
        this.aiService = aiService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("myForm", new MyForm());
        return "index";
    }

    @PostMapping("/")
    public String getQuestion(@ModelAttribute("myForm") MyForm myForm, Model model) {
        log.info("name: {}", myForm.getName() + "\n photo: " + myForm.getPhoto().getOriginalFilename());
        String photoText = this.imageService.uploadImage(myForm);
        if (photoText == "File upload failed" || photoText == "File not founded") {
            model.addAttribute("answer", photoText);
            return "index";
        }
        String answer = this.aiService.getChat(photoText);
        model.addAttribute("answer", answer);
        return "index";

    }
}
