package com.person.api.services;

import com.person.api.DTO.PersonDTO;
import com.person.api.entities.Person;
import com.person.api.repositories.PersonRepository;
import com.person.api.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonService {
    @Autowired
    private PersonRepository personRepository;

    @Transactional(readOnly = true)
    public PersonDTO findById(Long id) {
        Person personEntity = personRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Person not found"));
        return new PersonDTO(personEntity);
    }

    @Transactional(readOnly = true)
    public Page<PersonDTO> findAll(Pageable pageable) {
        Page<Person> people = personRepository.findAll(pageable);
        return people.map(PersonDTO::new);
    }

    @Transactional(readOnly = false)
    public PersonDTO insert(PersonDTO personDTO) {
        Person personEntity = new Person();
        copyDtoToEntity(personDTO, personEntity);
        personEntity = personRepository.save(personEntity);
        return new PersonDTO(personEntity);
    }

    @Transactional(readOnly = false)
    public PersonDTO update(Long id, PersonDTO personDTO) {
        try {
            Person personEntity = personRepository.getReferenceById(id);
            copyDtoToEntity(personDTO, personEntity);
            return new PersonDTO(personRepository.save(personEntity));
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Person not found");
        }
    }

    @Transactional(readOnly = false, propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        try {
            personRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Person not found");
        }
    }

    private void copyDtoToEntity(PersonDTO personDTO, Person person) {
        person.setName(personDTO.getName());
        person.setEmail(personDTO.getEmail());
        person.setBirthDate(personDTO.getBirthDate());
    }

}
