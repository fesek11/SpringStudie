package ua1.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua1.models.Person;

import java.util.Date;

@Component
public class DateValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        Date date = new Date();

        errors.rejectValue("date", "", "Incorrect date");
    }
}
