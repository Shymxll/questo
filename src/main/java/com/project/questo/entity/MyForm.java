package com.project.questo.entity;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class MyForm {
    private String name;
    private MultipartFile photo;
}
