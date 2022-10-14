package com.person.api.services;

import com.person.api.DTO.PersonDTO;
import com.person.api.entities.Person;
import com.person.api.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {
    @Autowired
    private PersonRepository personRepository;

    public PersonDTO findById(Long id) {
        Person personEntity = personRepository.findById(id).get();
        return new PersonDTO(personEntity);
    }
}
