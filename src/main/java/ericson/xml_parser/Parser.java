package ericson.xml_parser;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Parser {

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
     DocumentBuilderFactory dbFactory = DocumentBuilderFactory
       .newInstance();
     DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
     Document doc = dBuilder.parse(fXmlFile);

     doc.getDocumentElement().normalize();
     System.out.println("Root element :"
       + doc.getDocumentElement().getNodeName());
     NodeList nList = doc.getElementsByTagName("employee");  // get details of 'staff' node from the file
     
     System.out.println("----------------------------");

     for (int temp = 0; temp < nList.getLength(); temp++) {
      Node nNode = nList.item(temp);
      System.out.println("\nCurrent Element :"
        + nNode.getNodeName());
      if (nNode.getNodeType() == Node.ELEMENT_NODE) {
       Element eElement = (Element) nNode;
       System.out.println("Staff id : "
         + eElement.getAttribute("id"));
       System.out.println("First Name : "
         + eElement
           .getElementsByTagName("firstname")
           .item(0).getTextContent());
       System.out.println("Last Name : "
         + eElement.getElementsByTagName("lastname")
           .item(0).getTextContent());
       System.out.println("Nick Name : "
         + eElement.getElementsByTagName("nickname")
           .item(0).getTextContent());
       System.out.println("Salary : "
         + eElement.getElementsByTagName("salary")
           .item(0).getTextContent());

      }
     }

    } else if (listOfFiles[i].isDirectory()) {
     System.out.println("Directory " + listOfFiles[i].getName());
    }
   }

  } catch (Exception e) {
   e.printStackTrace();
  }
 }

}