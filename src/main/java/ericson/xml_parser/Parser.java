package ericson.xml_parser;

import java.io.File;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

import java.io.IOException;

public class Parser {
    private final ObjectMapper objectMapper = new ObjectMapper();
	

    public static Map<String, Object> parseXml(File xmlFile) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(xmlFile);

        document.getDocumentElement().normalize();
        Element rootElement = document.getDocumentElement();
        resultMap.put(rootElement.getNodeName(), parseElement(rootElement));
        return resultMap;
    }

    private static Map<String, Object> parseElement(Element element) {
        Map<String, Object> elementMap = new HashMap<>();
        NodeList childNodes = element.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node childNode = childNodes.item(i);

            if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                Element childElement = (Element) childNode;
                elementMap.put(childElement.getNodeName(), parseElement(childElement));
            } else if (childNode.getNodeType() == Node.TEXT_NODE) {
                String textContent = childNode.getTextContent().trim();
                if (!textContent.isEmpty()) {
                    elementMap.put("value", textContent);
                }
            }
        }
        return elementMap;
    }
    
    private void storeInFile(Map<String, Object> configurations, String fileName) {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(fileName), configurations);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 public static void main(String[] args) {
  try {

   File folder = new File("/home/ahmed-hodhod/testat/");
   File[] listOfFiles = folder.listFiles(); // get all files details

   for (int i = 0; i < listOfFiles.length; i++) {
    if (listOfFiles[i].isFile()
      && listOfFiles[i].toString().contains(".xml")) { // check file is xml or not
     System.out.println("\n*** File " + listOfFiles[i].getName()
       + " ***\n");

     File fXmlFile = new File("/home/ahmed-hodhod/testat/"
       + listOfFiles[i].getName());
    //  DocumentBuilderFactory dbFactory = DocumentBuilderFactory
    //    .newInstance();
    //  DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
    //  Document doc = dBuilder.parse(fXmlFile);
    
    //  doc.getDocumentElement().normalize();
    //  System.out.println("Root element :"
    //    + doc.getDocumentElement().getNodeName());
     
     
     Map<String, Object> configurations = parseXml(fXmlFile);
     System.out.println("Parsed XML Configurations: " + configurations);
     
      final ObjectMapper objectMapper = new ObjectMapper();
     objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File("configurations.json"), configurations);

     
     //NodeList nList = doc.getElementsByTagName("bulkCmConfigDataFile");  // get details of 'staff' node from the file
     

    //  for (int temp = 0; temp < nList.getLength(); temp++) {
    //   Node nNode = nList.item(temp);
    //   System.out.println("\nCurrent Element :"
    //     + nNode.getNodeName());
    //   if (nNode.getNodeType() == Node.ELEMENT_NODE) {
    //    Element eElement = (Element) nNode;
    //    System.out.println("Staff id : "
    //      + eElement.getAttribute("id"));
    //    System.out.println("First Name : "
    //      + eElement
    //        .getElementsByTagName("firstname")
    //        .item(0).getTextContent());
    //    System.out.println("Last Name : "
    //      + eElement.getElementsByTagName("lastname")
    //        .item(0).getTextContent());
    //    System.out.println("Nick Name : "
    //      + eElement.getElementsByTagName("nickname")
    //        .item(0).getTextContent());
    //    System.out.println("Salary : "
    //      + eElement.getElementsByTagName("salary")
    //        .item(0).getTextContent());

    //   }
    //  }

    } else if (listOfFiles[i].isDirectory()) {
     System.out.println("Directory " + listOfFiles[i].getName());
    }
   }

  } catch (Exception e) {
   e.printStackTrace();
  }
 }

}