package ch.heigvd.amt.xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;

public class Sax {

    public static void main(String[] args) throws Exception {
        // Initialize the parser
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();

        // Create the handler
        DefaultHandler handler = new DefaultHandler() {

            boolean isName = false;
            boolean isAge = false;

            @Override
            public void startDocument() {
                System.out.println("Start of document");
            }

            @Override
            public void startElement(String uri, String localName, String qName, Attributes attributes) {
                if (qName.equalsIgnoreCase("person")) {
                    System.out.println("Start of person");
                } else if (qName.equalsIgnoreCase("name")) {
                    isName = true;
                } else if (qName.equalsIgnoreCase("age")) {
                    isAge = true;
                }
            }

            @Override
            public void characters(char[] ch, int start, int length) {
                if (isName) {
                    System.out.println("  Name: " + new String(ch, start, length));
                    isName = false;
                } else if (isAge) {
                    System.out.println("  Age: " + new String(ch, start, length));
                    isAge = false;
                }
            }

            @Override
            public void endElement(String uri, String localName, String qName) {
                if (qName.equalsIgnoreCase("person")) {
                    System.out.println("End of person");
                }
            }

            @Override
            public void endDocument() {
                System.out.println("End of document");
            }
        };

        // Parse the XML file
        File xmlFile = new File("persons.xml");
        saxParser.parse(xmlFile, handler);
    }
}
