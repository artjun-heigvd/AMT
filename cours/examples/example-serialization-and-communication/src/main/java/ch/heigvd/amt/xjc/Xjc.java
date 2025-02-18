package ch.heigvd.amt.xjc;

import ch.heigvd.amt.xjc.generated.Persons;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;

public class Xjc {

    public static void main(String[] args) throws Exception {
        // Create a list of Person objects
        var person1 = new Persons.Person();
        person1.setName("Alice");
        person1.setAge(30);

        var person2 = new Persons.Person();
        person2.setName("Bob");
        person2.setAge(25);

        var persons = new Persons();
        persons.getPerson().add(person1);
        persons.getPerson().add(person2);

        // Serialize to XML
        JAXBContext context = JAXBContext.newInstance(Persons.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        File file = new File("persons.xml");
        marshaller.marshal(persons, file);

        System.out.println("Serialized to persons.xml");

        // Deserialize from XML
        Unmarshaller unmarshaller = context.createUnmarshaller();
        Persons deserializedWrapper = (Persons) unmarshaller.unmarshal(file);

        System.out.println("Deserialized:");
        deserializedWrapper.getPerson().forEach(person -> System.out.println(person.getName() + " " + person.getAge()));
    }
}
