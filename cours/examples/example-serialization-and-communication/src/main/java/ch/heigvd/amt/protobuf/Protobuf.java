package ch.heigvd.amt.protobuf;

import ch.heigvd.amt.proto.PersonOuterClass;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Protobuf {

    public static void main(String[] args) throws Exception {
        // Create the persons
        var p1 = PersonOuterClass.Person.newBuilder()
                .setName("John")
                .setAge(42)
                .build();
        var p2 = PersonOuterClass.Person.newBuilder()
                .setName("Jane")
                .setAge(36)
                .build();
        var persons = PersonOuterClass.Persons.newBuilder()
                .addPersons(p1)
                .addPersons(p2)
                .build();

        // Set the file name
        var file = new File("persons.pb");

        // Serialize to binary
        try (var fos = new FileOutputStream(file)) {
            persons.writeTo(fos);
        }

        // Deserialize from binary
        try (var fis = new FileInputStream(file)) {
            var deserializedWrapper = PersonOuterClass.Persons.parseFrom(fis);
            deserializedWrapper.getPersonsList().forEach(person -> System.out.println(person.getName() + " " + person.getAge()));
        }
    }

}
