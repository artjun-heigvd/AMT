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
        System.out.println("Validating " + object);
        for (Field field : object.getClass().getDeclaredFields()) {
            validateField(field, object);
        }
    }

    /**
     * This method validates a field by checking all its annotations.
     *
     * @param field the field to validate.
     * @param object the object containing the field.
     */
    public static void validateField(Field field, Object object) {
        for (Annotation annotation : field.getAnnotations()) {
            if (annotation instanceof NotNull) {
                validateNotNull(field, object);
            } else if (annotation instanceof Range range) {
                validateRange(range, field, object);
            } else if (annotation instanceof Regex regex) {
                validateRegex(regex, field, object);
            }
        }
    }

    /**
     * This method validates that a field is not null with the @NotNull annotation
     * and prints the result.
     *
     * @param field the field to validate.
     * @param object the object containing the field.
     */
    private static void validateNotNull(Field field, Object object) {
        field.setAccessible(true);
        try {
            if (field.get(object) == null) {
                System.out.println("- " + field.getName() + " is null");
            } else {
                System.out.println("- " + field.getName() + " is valid");
            }
        } catch (IllegalAccessException e) {
            System.out.println("- " + field.getName() + " is not accessible");
        }
    }

    /**
     * This method validates that a field is in a given range with the @Range annotation
     * and prints the result.
     *
     * @param range the range annotation
     * @param field the field to validate
     * @param object the object containing the field
     */
    private static void validateRange(Range range, Field field, Object object) {
        var min = range.min();
        var max = range.max();
        field.setAccessible(true);
        try {
            Number fieldValue = (Number) field.get(object);
            if (fieldValue.doubleValue() < min || fieldValue.doubleValue() > max) {
                System.out.println("- " + field.getName() + " is not in range [" + min + ", " + max + "]");
            } else {
                System.out.println("- " + field.getName() + " is valid");
            }
        } catch (IllegalAccessException e) {
            System.out.println("- " + field.getName() + " is not accessible");
        } catch (ClassCastException e) {
            System.out.println("- " + field.getName() + " is not a Number");
        }
    }

    /**
     * This method validates that a field matches a given regex with the @Regex annotation
     * and prints the result.
     *
     * @param regex the regex annotation
     * @param field the field to validate
     * @param object the object containing the field
     */
    public static void validateRegex(Regex regex, Field field, Object object) {
        var value = regex.value();
        field.setAccessible(true);
        try {
            String fieldValue = (String) field.get(object);
            if (fieldValue != null && !fieldValue.matches(value)) {
                System.out.println("- " + field.getName() + " does not match regex " + value);
            } else {
                System.out.println("- " + field.getName() + " is valid");
            }
        } catch (IllegalAccessException e) {
            System.out.println("- " + field.getName() + " is not accessible");
        } catch (ClassCastException e) {
            System.out.println("- " + field.getName() + " is not a Number");
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
