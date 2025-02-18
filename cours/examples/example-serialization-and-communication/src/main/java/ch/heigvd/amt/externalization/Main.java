package ch.heigvd.amt.externalization;

import java.io.*;

class Person implements Externalizable {
    private static final long serialVersionUID = 1L;
    private String name;
    private int age;

    public Person() {
        // Required for Externalizable
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(name);
        out.writeInt(age);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        name = in.readUTF();
        age = in.readInt();
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}

public class Main {
    public static void main(String[] args) throws Exception {
        var p1 = new Person("John", 42);
        var p2 = new Person("Jane", 36);
        // Write the objects to a file
        try (var ObjectOutputStream = new ObjectOutputStream(new FileOutputStream("persons.ser"))) {
            ObjectOutputStream.writeObject(p1);
            ObjectOutputStream.writeObject(p2);
        }
        // Read the objects from the file
        try (var ObjectInputStream = new ObjectInputStream(new FileInputStream("persons.ser"))) {
            var p3 = (Person) ObjectInputStream.readObject();
            System.out.println(p3);
            var p4 = (Person) ObjectInputStream.readObject();
            System.out.println(p4);
        }
    }
}