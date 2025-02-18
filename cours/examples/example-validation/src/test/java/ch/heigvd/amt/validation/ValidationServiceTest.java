package ch.heigvd.amt.validation;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import org.jboss.logging.Logger;
import org.junit.jupiter.api.Test;

@QuarkusTest
class ValidationServiceTest {

    private static Logger LOG = Logger.getLogger(ValidationServiceTest.class);

    @Inject
    ValidationService validationService;

    @Test
    void dontForgetToOptIn() {
        // Validation is not magically performed on entities that are annotated with Jakarta Validation annotations
        Person person = new Person();
        validationService.dontForgetToOptIn(person);
    }

    @Test
    void automaticValidation() {
        try {
            Person person = new Person();
            validationService.automaticValidation(person);
        } catch (ConstraintViolationException e) {
            e.getConstraintViolations().forEach(violation -> LOG.error(violation.getMessage()));
        }
    }

    @Test
    void programmaticValidation() {
        Person person = new Person();

        LOG.info("Validating the empty person");
        validationService.programmaticValidation(person);

        person.firstName = "John";
        person.lastName = "Doe";
        person.yearOfBirth = 2000;
        person.email = "test";

        LOG.info("Validating the person with invalid email");
        validationService.programmaticValidation(person);

        person.email = "test@test.com";

        LOG.info("Validating the person with valid data");
        validationService.programmaticValidation(person);
        System.out.println("The person is valid");
    }
}