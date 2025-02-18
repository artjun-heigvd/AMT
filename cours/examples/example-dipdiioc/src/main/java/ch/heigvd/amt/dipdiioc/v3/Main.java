package ch.heigvd.amt.dipdiioc.v3;

// The Logger abstraction illustrates the dependency inversion principle (DIP), which corresponds to the "D" in the SOLID acronym.
interface Logger {
    void logMessage(String message);
}

// The low-level ConsoleLogger implements the abstraction.
class ConsoleLogger implements Logger {
    public void logMessage(String message) {
        System.out.println("Log message to console: " + message);
    }
}

// The Application depends on the Logger abstraction.
class Application {

    private Logger logger;

    public Application() {}

    public Application(Logger logger) {
        this.logger = logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public void performOperation() {
        logger.logMessage("Performing an operation");
    }
}

public class Main {
    public static void main(String[] args) {
        // The concrete ConsoleLogger is injected into the Application via the constructor.
        var app = new Application(new ConsoleLogger());
        // Or via a setter.
        app.setLogger(new ConsoleLogger());
        app.performOperation();
    }
}
