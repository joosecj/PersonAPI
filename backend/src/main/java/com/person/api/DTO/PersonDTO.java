package com.person.api.DTO;

import com.person.api.entities.Person;
import com.person.api.services.validation.PersonInsertValid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@PersonInsertValid
public class PersonDTO {
    private Long id;
    @Size(min = 3, max = 80, message = "Name must have a minimum of 3 and a maximum of 80 characters")
    @NotBlank(message = "Required field")
    private String name;
    @Email(message = "Required field")
    @NotBlank(message = "Invalid email")
    private String email;
    @PastOrPresent(message = "Date cannot be future")
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
