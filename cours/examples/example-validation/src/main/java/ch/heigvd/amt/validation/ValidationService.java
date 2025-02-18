package ch.heigvd.amt.validation;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import org.jboss.logging.Logger;

import java.util.Set;

@ApplicationScoped
public class ValidationService {

    private static Logger LOG = Logger.getLogger(ValidationService.class);

    @Inject
    Validator validator;

    public void dontForgetToOptIn(Person person) {
        // The person has not been validated and may contain invalid data
    }

    public void automaticValidation(@Valid Person person) {
        // The person is automatically validated
    }

    public void programmaticValidation(Person person) {
        Set<ConstraintViolation<Person>> violations = validator.validate(person);
        violations.forEach(violation -> LOG.error(violation.getMessage()));
    }
}
