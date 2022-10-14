package com.person.api.services;

import com.person.api.DTO.PersonDTO;
import com.person.api.entities.Person;
import com.person.api.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonService {
    @Autowired
    private PersonRepository personRepository;

    @Transactional(readOnly = true)
    public PersonDTO findById(Long id) {
        Person personEntity = personRepository.findById(id).get();
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
        Person personEntity = personRepository.getReferenceById(id);
        copyDtoToEntity(personDTO, personEntity);
        return new PersonDTO(personRepository.save(personEntity));
    }

    @Transactional(readOnly = false)
    public void delete(Long id) {
        personRepository.deleteById(id);
    }

    private void copyDtoToEntity(PersonDTO personDTO, Person person) {
        person.setName(personDTO.getName());
        person.setEmail(personDTO.getEmail());
        person.setBirthDate(personDTO.getBirthDate());
    }

}
