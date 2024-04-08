package org.wildfly.validator;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.wildfly.exception.ValidationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@ApplicationScoped
public class CustomValidator {

    @Inject
    private Validator validator;

    public void validate(Object obj) {
        Set<ConstraintViolation<Object>> violations = validator.validate(obj);
        if (!violations.isEmpty()) {
            List<String> errorMessages = new ArrayList<>();
            for (ConstraintViolation<Object> violation : violations) {
                errorMessages.add(violation.getMessage());
            }
            throw new ValidationException(errorMessages);
        }
    }

}
