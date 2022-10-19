package com.person.api.repositories;

import com.person.api.entities.Person;
import com.person.api.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Optional;

@DataJpaTest
public class PersonRepositoryTests {

    @Autowired
    private PersonRepository personRepository;

    private Long existsId;
    private Long nonExistingID;
    private Long countTotalPerson;

    @BeforeEach
    void setUp() throws Exception {
        existsId = 1L;
        nonExistingID = 1000L;
        countTotalPerson = 21L;
    }

    @Test
    public void findByIdShouldReturnNonEmptyOptionalWhenIdExists() {
        Optional<Person> result = personRepository.findById(existsId);
        Assertions.assertTrue(result.isPresent());
    }

    @Test
    public void findByIdShouldNotReturnEmptyOptionalWhenIdDoesNotExists() {
        Optional<Person> result = personRepository.findById(nonExistingID);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void saveShouldPersistWithAutoincrementWhenIdIsNull() {
        Person person = Factory.createPerson();
        person.setId(null);
        person = personRepository.save(person);
        Assertions.assertNotNull(person.getId());
        Assertions.assertEquals(countTotalPerson + 1, person.getId());
    }

    @Test
    public void updateShouldPersisWhenIdNotNull() {
        Person person = personRepository.getReferenceById(existsId);
        personRepository.save(person);
        Assertions.assertNotNull(person.getId());
    }

    @Test
    public void deleteShouldDeleteObjectWhenIdExists() {
        personRepository.deleteById(existsId);
        Optional<Person> result = personRepository.findById(existsId);
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    public void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
            personRepository.deleteById(nonExistingID);
        });
    }

}
