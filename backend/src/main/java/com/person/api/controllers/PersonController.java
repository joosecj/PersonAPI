package com.person.api.controllers;

import com.person.api.DTO.PersonDTO;
import com.person.api.entities.Person;
import com.person.api.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/pessoa")
public class PersonController {
    @Autowired
    private PersonService personService;

    @GetMapping(value = "/{id}")
    public PersonDTO findById(@PathVariable Long id) {
        return personService.findById(id);
    }

    @GetMapping
    public Page<PersonDTO> findAll (Pageable pageable) {
        return personService.findAll(pageable);
    }

}
