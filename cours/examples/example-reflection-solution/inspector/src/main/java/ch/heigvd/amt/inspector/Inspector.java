package ch.heigvd.amt.inspector;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;

public class Inspector {

    /**
     * Inspect an object and print its class name, fields and methods.
     * @param object the object to inspect
     */
    public static void inspect(Object object) {
        try {
            Class<?> clazz = object.getClass();
            System.out.println("Class name: " + clazz.getName());

            System.out.println("Fields:");
            // Sort method by alphabetical order as "elements in the returned array are not sorted and are not in any particular order."
            for (Field field :  Arrays.stream(clazz.getDeclaredFields()).sorted(Comparator.comparing(Field::getName)).toList()) {
                field.setAccessible(true);
                System.out.println(" - " + field.getName() + ": " + field.get(object));
            }

            System.out.println("Methods:");
            // Sort method by alphabetical order as "elements in the returned array are not sorted and are not in any particular order."
            for (Method method : Arrays.stream(clazz.getDeclaredMethods()).sorted(Comparator.comparing(Method::getName)).toList()) {
                System.out.println(" - " + method.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Demonstrate the use of the Inspector class by inspecting a dog.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        inspect(new Dog("Buddy", 5));
    }
}
