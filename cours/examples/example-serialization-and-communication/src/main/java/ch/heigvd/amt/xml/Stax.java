package ch.heigvd.amt.xml;


import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;

public class Stax {

    public static void main(String[] args) throws Exception {
        // Initialize the file and the parser
        FileInputStream xmlFile = new FileInputStream("persons.xml");
        XMLInputFactory factory = XMLInputFactory.newInstance();

        // Parse the XML file and iterate over the events
        XMLEventReader eventReader = factory.createXMLEventReader(xmlFile);
        boolean isName = false;
        boolean isAge = false;
        System.out.println("Start of document");
        while (eventReader.hasNext()) {
            XMLEvent event = eventReader.nextEvent();
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                String elementName = startElement.getName().getLocalPart();
                if (elementName.equalsIgnoreCase("person")) {
                    System.out.println("Start of person");
                } else if (elementName.equalsIgnoreCase("name")) {
                    isName = true;
                } else if (elementName.equalsIgnoreCase("age")) {
                    isAge = true;
                }
            } else if (event.isCharacters()) {
                Characters characters = event.asCharacters();
                if (isName) {
                    System.out.println("  Name: " + characters.getData());
                    isName = false;
                } else if (isAge) {
                    System.out.println("  Age: " + characters.getData());
                    isAge = false;
                }
            } else if (event.isEndElement()) {
                EndElement endElement = event.asEndElement();
                if (endElement.getName().getLocalPart().equalsIgnoreCase("person")) {
                    System.out.println("End of person");
                }
            }
        }
        System.out.println("End of document");
        eventReader.close();
    }
}
