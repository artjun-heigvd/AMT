package ch.heigvd.amt.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * This class is used to validate annotated objects at runtime.
 */
public class Validator {

    /**
     * This method validates an object by checking all its fields.
     * @param object the object to validate.
     */
    public static void validate(Object object) {
        // TODO: implement this method to validate an object by checking all its fields
        Class<?> clazz = object.getClass();
        System.out.println(object);
        for(Field field : clazz.getDeclaredFields()) {
            Object fieldValue = null;
            field.setAccessible(true);
            try{
                fieldValue = field.get(object);
            }catch(IllegalAccessException ignored) {}
            for(Annotation annotation : field.getAnnotations()) {
                Class<?> annotationType = annotation.annotationType();
                switch(annotationType.getSimpleName()) {
                    case "NotNull":
                        if(fieldValue == null) {
                            System.out.println("- " + field.getName() + " is null");
                        }else{
                            System.out.println("- " + field.getName() + " is valid");
                        }
                        break;
                    case "Range":
                        System.out.println("Range annotation");
                        break;
                    case "Regex":
                        System.out.println("Regex annotation");
                        break;
                    default:
                        System.out.println("Annotation validation not implemented for " + annotationType.getSimpleName());
                }
            }
        }
    }

    /**
     * This class is used to test the validator.
     * Its fields are annotated with the validator annotations.
     */
    static class Person {

        @NotNull
        String username;

        @Range(min = 0, max = 100)
        int age;

        @Regex("[a-z]+@[a-z]+\\.[a-z]+")
        String email;

        @Regex("\\+[0-9]{2} [0-9]{2} [0-9]{3} [0-9]{2} [0-9]{2}")
        String phoneNumber;

        public Person(String username, int age, String email, String phoneNumber) {
            this.username = username;
            this.age = age;
            this.email = email;
            this.phoneNumber = phoneNumber;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "username='" + username + '\'' +
                    ", age=" + age +
                    ", email='" + email + '\'' +
                    ", phoneNumber='" + phoneNumber + '\'' +
                    '}';
        }
    }

    public static void main(String... args) {
        validate(new Person("john", 42, "john@example.com", "+41 79 123 45 67"));
        validate(new Person(null, 200, "john@example", "079 123 45 67"));
    }
}
