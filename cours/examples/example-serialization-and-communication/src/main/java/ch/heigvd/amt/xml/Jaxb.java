package ch.heigvd.amt.xml;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.UnmarshallerHandler;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.List;

@XmlRootElement(name = "person")
@XmlType(propOrder = {"name", "age"})
class Person {

    private String name;
    private int age;

    public Person() {
        // Required by JAXB
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}

@XmlRootElement(name = "persons")
class Persons {

    private List<Person> persons;

    @XmlElement(name = "person")
    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }
}


public class Jaxb {
    public static void main(String[] args) throws Exception {
        // Create a list of Person objects
        Person person1 = new Person("Alice", 30);
        Person person2 = new Person("Bob", 25);
        Persons persons = new Persons();
        persons.setPersons(Arrays.asList(person1, person2));

        // Serialize to XML
        JAXBContext context = JAXBContext.newInstance(Persons.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        File file = new File("persons.xml");
        marshaller.marshal(persons, file);

        System.out.println("Serialized to persons.xml");

        // Deserialize with JAXB
        Unmarshaller unmarshaller = context.createUnmarshaller();
        Persons deserializedWrapper = (Persons) unmarshaller.unmarshal(file);
        System.out.println("Deserialized (JAXB):");
        deserializedWrapper.getPersons().forEach(System.out::println);

        // Deserialize with SAX and JAXB
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = saxParserFactory.newSAXParser();

        XMLReader xmlReader = saxParser.getXMLReader();
        Unmarshaller saxUnmarshaller = context.createUnmarshaller();
        UnmarshallerHandler unmarshallerHandler = saxUnmarshaller.getUnmarshallerHandler();
        xmlReader.setContentHandler(unmarshallerHandler);

        FileInputStream inputStream = new FileInputStream(file);
        InputSource inputSource = new InputSource(inputStream);
        xmlReader.parse(inputSource);

        Persons saxDeserializedWrapper = (Persons) unmarshallerHandler.getResult();
        System.out.println("Deserialized (JAXB and SAX):");
        saxDeserializedWrapper.getPersons().forEach(System.out::println);

        // Deserialize with StAX and JAXB
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        XMLEventReader eventReader = xmlInputFactory.createXMLEventReader(new FileInputStream(file));

        Unmarshaller staxUnmarshaller = context.createUnmarshaller();
        Persons staxDeserialized = (Persons) staxUnmarshaller.unmarshal(eventReader);

        System.out.println("Deserialized with StAX and JAXB:");
        staxDeserialized.getPersons().forEach(System.out::println);
    }
}
