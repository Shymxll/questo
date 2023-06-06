package com.project.questo.service;

import com.mindee.MindeeClient;
import com.mindee.MindeeClientInit;
import com.mindee.DocumentToParse;
import com.mindee.parsing.common.Document;
import com.mindee.parsing.invoice.InvoiceV4Inference;
import com.project.questo.entity.MyForm;

import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;


@Service
@Slf4j
public class ImageService {
   
    public String uploadImage(MyForm myForm){
        String name = myForm.getName();
        String photo = myForm.getPhoto().getOriginalFilename();
        MultipartFile file = myForm.getPhoto();
        if (!file.isEmpty()) {
            try {
                String currentDirectory = System.getProperty("user.dir");

                String uploadDirectory =currentDirectory+ "/src/main/resources/imgs/";
                String filePath = uploadDirectory + file.getOriginalFilename();
                File dest = new File(filePath);
                FileUtils.copyInputStreamToFile(file.getInputStream(), dest);
                return this.readImage(filePath,"eng");
            } catch (Exception e) {

                log.error("File upload failed", e);
                return e.getMessage();
            }
        } else {
            log.error("File not founded");
            return "File not founded";
        }
    }
    public String readImage(String path ,String language) throws IOException{
        ITesseract tesseract = new Tesseract();
        tesseract.setLanguage(language);

            try {
        
                File imageFile = new File(path);
                tesseract.setDatapath("src/main/resources/tessdata");
                String result = tesseract.doOCR(imageFile);
                
            
                
                //delete the file
                imageFile.delete();
                return result;
            } catch (TesseractException e) {
                return "Error while reading image";
            }
        
      }
      
}
