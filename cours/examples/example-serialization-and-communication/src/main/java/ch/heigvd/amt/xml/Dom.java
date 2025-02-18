package ch.heigvd.amt.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class Dom {

    public static void main(String[] args) throws Exception {
        // Parse the XML file
        File xmlFile = new File("persons.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(xmlFile);

        // Get the root element
        Element root = document.getDocumentElement();
        System.out.println("Root Element: " + root.getNodeName());

        // Get all <person> elements
        NodeList personList = document.getElementsByTagName("person");
        for (int i = 0; i < personList.getLength(); i++) {
            Node personNode = personList.item(i);
            if (personNode.getNodeType() == Node.ELEMENT_NODE) {
                Element personElement = (Element) personNode;
                String name = personElement.getElementsByTagName("name").item(0).getTextContent();
                String age = personElement.getElementsByTagName("age").item(0).getTextContent();
                System.out.println("Person " + (i + 1) + ":");
                System.out.println("  Name: " + name);
                System.out.println("  Age: " + age);
            }
        }
    }
}
