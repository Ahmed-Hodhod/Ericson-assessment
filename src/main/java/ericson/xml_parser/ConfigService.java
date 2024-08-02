package ericson.xml_parser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.File;
import java.util.Map;

@Service
public class ConfigService {

    @Autowired
    private ConfigurationRepository configurationRepository;

    public void processAndSaveXml(File xmlFile) throws Exception {
        Map<String, Object> parsedData = Parser.parseXml(xmlFile);
        saveToMongoDB(parsedData);
    }

    private void saveToMongoDB(Map<String, Object> data) {
        saveMapRecursively(data, "");
    }

    private void saveMapRecursively(Map<String, Object> map, String prefix) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = prefix.isEmpty() ? entry.getKey() : prefix + "." + entry.getKey();
            Object value = entry.getValue();

            if (value instanceof Map) {
                saveMapRecursively((Map<String, Object>) value, key);
            } else {
                Configuration configuration = new Configuration();
                configuration.setKey(key);
                configuration.setValue(value.toString());
                configurationRepository.save(configuration);
            }
        }
    }
}