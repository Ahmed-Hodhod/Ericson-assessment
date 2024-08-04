package ericson.xml_parser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileOutputStream;

@RestController
@RequestMapping("/api/configurations")
public class ConfigController {

    @Autowired
    private ConfigService service;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadXmlFile(@RequestParam("file") MultipartFile file) {
        try {
            // Convert MultipartFile to File
            File convFile = new File(file.getOriginalFilename());
            try (FileOutputStream fos = new FileOutputStream(convFile)) {
                fos.write(file.getBytes());
            }
            System.out.println("File received: " + file.getOriginalFilename() + ", Size: " + file.getSize());

            // Process and save the XML file
            service.processAndSaveXml(convFile);

            // Delete the temporary file
            convFile.delete();

            return ResponseEntity.ok("File processed and saved successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error processing file: " + e.getMessage());
        }
    }


}