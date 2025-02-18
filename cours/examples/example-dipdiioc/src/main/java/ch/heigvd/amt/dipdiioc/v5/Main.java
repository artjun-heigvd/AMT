package ch.heigvd.amt.dipdiioc.v5;

import jakarta.inject.Inject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

interface Logger {
    void logMessage(String message);
}

// The low-level ConsoleLogger implements the abstraction.
class ConsoleLogger implements Logger {
    public void logMessage(String message) {
        System.out.println("Log message to console: " + message);
    }
}

// The actions performed by the Application on the Logger can now be tested.
class TestableLogger implements Logger {

    private String lastMessage;

    public void logMessage(String message) {
        lastMessage = message;
    }

    public String getLastMessage() {
        return lastMessage;
    }
}


// Dependency injection (DI) enable the Application to be decoupled from the Logger implementation.
// Here, the dependency is injected by a framework via an annotation on a field.
class Application {

    // The high-level Application depends on the abstraction.
    @Inject
    private Logger logger;

    public void performOperation() {
        logger.logMessage("Performing an operation");
    }
}

// Frameworks, such as Quarkus or Spring, usually implements the inversion of control (IoC) and the dependency injection (DI) patterns.
class Injector {

    private Map<Class, Supplier<Object>> bindings = new HashMap<>();

    // Registers a binding between an abstraction and an instance.
    public <T> void bind(Class<T> abstraction, T implementation) {
        bindings.put(abstraction, () -> implementation);
    }

    // Registers a binding between an abstraction and a class.
    public <T> void bind(Class<T> abstraction, Class<? extends T> implementation) {
        bindings.put(abstraction, () -> {
            try {
                return implementation.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    // Creates an instance of an abstraction with a registered binding or with its declared constructor.
    private <T> T init(Class<T> abstraction) {
        return (T) bindings.getOrDefault(abstraction, () -> {
            try {
                return abstraction.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).get();
    }

    // Creates an instance of an abstraction and inject its dependencies.
    public <T> T create(Class<T> abstraction) {
        // Inversion of Control (IoC) delegates the creation of objects to a Framework.
        T object = init(abstraction);

        // Dependency Injection (DI) injects the dependencies of an object.
        for (Field field : abstraction.getDeclaredFields()) {
            for (Annotation annotation : field.getAnnotations()) {
                if (annotation instanceof Inject) {
                    try {
                        field.setAccessible(true);
                        field.set(object, bindings.get(field.getType()).get());
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

        return object;
    }
}

// Simple assertion utility.
class Assert {
    public static void assertEquals(Object expected, Object actual) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("Expected '" + expected + "' but got '" + actual + "'");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        // In general, the initialization of the Framework is hidden.
        // The application developer typically interact with the Framework via configuration files.
        // Here, we initialize the framework programmatically.
        var framework = new Injector();
        var logger = new TestableLogger();
        framework.bind(Logger.class, logger);

        // The creation of the Application object can now be delegated to the Framework.
        Application app = framework.create(Application.class);
        app.performOperation();

        // The test can now be performed on the Logger.
        Assert.assertEquals("Performing an operation", logger.getLastMessage());
        Assert.assertEquals("Failing test", logger.getLastMessage());
    }
}
