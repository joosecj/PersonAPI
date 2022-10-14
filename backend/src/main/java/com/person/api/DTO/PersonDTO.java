package com.person.api.DTO;

import com.person.api.entities.Person;

import java.time.LocalDate;

public class PersonDTO {
    private Long id;
    private String name;
    private String email;

    private LocalDate birthDate;

    public PersonDTO(Long id, String name, String email, LocalDate birthDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.birthDate = birthDate;
    }

    public PersonDTO(Person personEntity) {
        id = personEntity.getId();
        name = personEntity.getName();
        email = personEntity.getEmail();
        birthDate = personEntity.getBirthDate();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }
}
