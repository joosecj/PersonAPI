package com.person.api.services.validation;

import com.person.api.controllers.handlers.FieldMessage;
import com.person.api.DTO.PersonDTO;
import com.person.api.entities.Person;
import com.person.api.repositories.PersonRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class PersonInsertValidator implements ConstraintValidator<PersonInsertValid, PersonDTO> {
    @Autowired
    private PersonRepository personRepository;

    @Override
    public void initialize(PersonInsertValid ann) {
    }

    @Override
    public boolean isValid(PersonDTO personDTO, ConstraintValidatorContext context) {

        List<FieldMessage> fieldMessageList = new ArrayList<>();

        Person person = personRepository.findByEmail(personDTO.getEmail());
        if (person != null) {
            fieldMessageList.add(new FieldMessage("email", "E-mail already registered"));
        }

        for (FieldMessage e : fieldMessageList) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }
        return fieldMessageList.isEmpty();
    }
}
