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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;

import org.apache.commons.io.FileUtils;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
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
                

                String uploadDirectory ="src/main/resources/imgs/" + "Screenshot_2.png";
                String filePath = uploadDirectory + file.getOriginalFilename();
               
                //File dest = new File(filePath);
                
                //FileUtils.copyInputStreamToFile(file.getInputStream(), dest);
                return this.readImageTerract(uploadDirectory,"tur");
            } catch (Exception e) {

                log.error("File upload failed", e);
                return e.getMessage();
            }
        } else {
            log.error("File not founded");
            return "File not founded";
        }
    }
    public String readImageTerract(String path ,String language) throws IOException{
        ITesseract tesseract = new Tesseract();
        tesseract.setLanguage(language);

            try {
        
                File imageFile = new File(path);
                
                String result = tesseract.doOCR(imageFile);
                
            
                
                //delete the file
                imageFile.delete();
                return result;
            } catch (TesseractException e) {
                return "Error while reading image";
            }
        
      }

      public String readImageTika(File file){
        String content = null;
        try {
            Tika tika = new Tika();
            byte[] fileContent;
            fileContent = Files.readAllBytes(file.toPath());
       
             content =  tika.parseToString(new ByteArrayInputStream(fileContent));
             log.info("content: {}", content);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (TikaException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return content;
        }
      
}
