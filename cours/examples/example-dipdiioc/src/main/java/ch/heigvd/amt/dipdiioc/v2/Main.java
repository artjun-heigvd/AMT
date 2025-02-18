package ch.heigvd.amt.dipdiioc.v2;

// The Logger illustrates the dependency inversion principle (DIP), which corresponds to the "D" in the SOLID acronym.
interface Logger {
    void logMessage(String message);
}

// The low-level ConsoleLogger implements the abstraction.
class ConsoleLogger implements Logger {
    public void logMessage(String message) {
        System.out.println("Log message to console: " + message);
    }
}

class Application {

    // The high-level Application now depends on the abstraction.
    private Logger logger;

    // The dependency is injected via the constructor.
    public Application(Logger logger) {
        this.logger = logger;
    }

    public void performOperation() {
        logger.logMessage("Performing an operation");
    }
}

public class Main {
    public static void main(String[] args) {
        Application app = new Application(new ConsoleLogger());
        app.performOperation();
    }
}
