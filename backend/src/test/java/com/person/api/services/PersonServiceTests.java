package com.person.api.services;

import com.person.api.DTO.PersonDTO;
import com.person.api.entities.Person;
import com.person.api.repositories.PersonRepository;
import com.person.api.services.exceptions.DataBaseException;
import com.person.api.services.exceptions.ResourceNotFoundException;
import com.person.api.tests.Factory;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
public class PersonServiceTests {
    @InjectMocks
    private PersonService personService;
    @Mock
    private PersonRepository personRepository;
    private Long existingId;
    private Long nonExistingId;
    private Long dependentId;
    private Person person;
    private PersonDTO personDTO;
    private PageImpl<Person> page;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        nonExistingId = 1000L;
        dependentId = 4L;
        person = Factory.createPerson();
        personDTO = Factory.createPersonDTO();
        page = new PageImpl<>(List.of(person));

        when(personRepository.findAll((Pageable) ArgumentMatchers.any())).thenReturn(page);

        when(personRepository.save(ArgumentMatchers.any())).thenReturn(person);

        when(personRepository.findById(existingId)).thenReturn(Optional.of(person));
        when(personRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        when(personRepository.getReferenceById(existingId)).thenReturn(person);
        when(personRepository.getReferenceById(nonExistingId)).thenThrow(EntityNotFoundException.class);

        when(personRepository.save(person)).thenReturn(person);


        doNothing().when(personRepository).deleteById(existingId);
        doThrow(EmptyResultDataAccessException.class).when(personRepository).deleteById(nonExistingId);
        doThrow(DataIntegrityViolationException.class).when(personRepository).deleteById(dependentId);
    }

    @Test
    public void findByIdShouldReturnPersonDTOWhenIdExists() {
        PersonDTO result = personService.findById(existingId);
        Assertions.assertNotNull(result);
    }

    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            personService.findById(nonExistingId);
        });
    }

    @Test
    public void findAllPagedShouldReturnPage() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<PersonDTO> result = personService.findAll(pageable);

        Assertions.assertNotNull(result);
        Mockito.verify(personRepository, times(1)).findAll(pageable);
    }

    @Test
    public void saveShouldReturnPersonDTOWhenIdNull() {
        PersonDTO result = personService.insert(personDTO);
        Assertions.assertNotNull(result);
    }

    @Test
    public void updatedShouldReturnPersonDTOWhenIdExists() {
        PersonDTO result = personService.update(existingId, personDTO);
        Assertions.assertNotNull(result);
    }

    @Test
    public void updatedShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            personService.update(nonExistingId, personDTO);
        });
    }

    @Test
    public void deleteShouldDoNothingWhenIdExists() {
        Assertions.assertDoesNotThrow(() -> {
            personService.delete(existingId);
        });
        Mockito.verify(personRepository, times(1)).deleteById(existingId);
    }

    @Test
    public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            personService.delete(nonExistingId);
        });
        Mockito.verify(personRepository, times(1)).deleteById(nonExistingId);
    }

    @Test
    public void deleteShouldThrowDatabaseExceptionWhenDependedId() {
        Assertions.assertThrows(DataBaseException.class, () -> {
            personService.delete(dependentId);
        });
        verify(personRepository, times(1)).deleteById(dependentId);
    }
}
