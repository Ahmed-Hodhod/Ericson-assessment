
package ericson.xml_parser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;

@RestController
@RequestMapping("/api/configurations")
public class ConfigController {

    @Autowired
    private ConfigService service;

    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> uploadXmlFile(@RequestParam("file") MultipartFile file) {
        try {
            // Convert MultipartFile to File
            File convFile = new File(file.getOriginalFilename());
            try (FileOutputStream fos = new FileOutputStream(convFile)) {
                fos.write(file.getBytes());
            }
            
            System.out.println("File received: " + file.getOriginalFilename() + ", Size: " + file.getSize());

            // Process the XML file
            Map<String, Object> parsedData = service.processXml(convFile);

            // Delete the temporary file
            convFile.delete();

            return ResponseEntity.ok(parsedData);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }
}

// curl -X POST -F "file=@test.xml" http://localhost:8080/api/configurations/upload

