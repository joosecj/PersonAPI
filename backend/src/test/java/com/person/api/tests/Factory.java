package com.person.api.tests;

import com.person.api.DTO.PersonDTO;
import com.person.api.entities.Person;

import java.sql.Date;
import java.time.LocalDate;

public class Factory {

    public static Person createPerson() {
        Person person = new Person(1L, "Maria Rita", "ritamarias@gmail.com", Date.valueOf("1986-05-10").toLocalDate());
        return person;
    }

    public static PersonDTO createPersonDTO() {
        Person person = createPerson();
        return new PersonDTO(person);
    }

    public static PersonDTO createPersonCustomized(String name, String email, LocalDate date) {
        Person person = new Person(1L, name, email, date);
        return new PersonDTO(person);
    }
}
