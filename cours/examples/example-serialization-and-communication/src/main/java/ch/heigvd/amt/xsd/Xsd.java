package ch.heigvd.amt.xsd;

import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;

public class Xsd {

    public static void main(String[] args) throws Exception {
        try {
            // Load XML and XSD files
            File xmlFile = new File("persons.xml");
            File xsdFile = new File("src/main/resources/schema/persons.xsd");

            // Create SchemaFactory and Schema
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(xsdFile);

            // Create Validator and validate
            Validator validator = schema.newValidator();
            validator.validate(new javax.xml.transform.stream.StreamSource(xmlFile));
            System.out.println("XML is valid!");

        } catch (SAXException e) {
            System.out.println("XML is not valid: " + e.getMessage());
        }
    }
}
