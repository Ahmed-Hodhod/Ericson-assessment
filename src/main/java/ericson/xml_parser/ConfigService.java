

package ericson.xml_parser;

import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Map;

@Service
public class ConfigService {

    public Map<String, Object> processXml(File xmlFile) throws Exception {
        return Parser.parseXml(xmlFile);
    }
}
