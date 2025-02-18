package ch.heigvd.amt.dipdiioc.v1;

class ConsoleLogger {

    public void logMessage(String message) {
        System.out.println("Log message to console: " + message);
    }

}

class Application {

    // The high-level Application  depends directly on the low-level ConsoleLogger.
    private ConsoleLogger logger = new ConsoleLogger();

    public void performOperation() {
        logger.logMessage("Performing an operation");
    }
}

public class Main {

    public static void main(String[] args) {
        Application app = new Application();
        app.performOperation();
    }

}
