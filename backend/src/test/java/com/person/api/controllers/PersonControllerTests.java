package com.person.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.person.api.DTO.PersonDTO;
import com.person.api.repositories.PersonRepository;
import com.person.api.services.PersonService;
import com.person.api.services.exceptions.DataBaseException;
import com.person.api.services.exceptions.ResourceNotFoundException;
import com.person.api.tests.Factory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = PersonController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class PersonControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PersonService personService;

    @MockBean
    private PersonRepository personRepository;
    @Autowired
    private ObjectMapper mapper;
    private Long existingId;
    private Long nonExistingId;
    private Long dependentId;
    private PersonDTO personDTO;
    private PageImpl<PersonDTO> page;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        nonExistingId = 2L;
        dependentId = 3L;
        personDTO = Factory.createPersonDTO();
        page = new PageImpl<>(List.of(personDTO));

        when(personService.findById(existingId)).thenReturn(personDTO);
        when(personService.findById(nonExistingId)).thenThrow(ResourceNotFoundException.class);

        when(personService.findAll(any())).thenReturn(page);

        when(personService.insert(any())).thenReturn(personDTO);

        when(personService.update(eq(existingId), any())).thenReturn(personDTO);
        when(personService.update(eq(nonExistingId), any())).thenThrow(ResourceNotFoundException.class);

        doNothing().when(personService).delete(existingId);
        doThrow(ResourceNotFoundException.class).when(personService).delete(nonExistingId);
        doThrow(DataBaseException.class).when(personService).delete(dependentId);

    }

    @Test
    public void findByIdShouldReturnProductWhenIdExists() throws Exception {
        ResultActions result = mockMvc.perform(get("/pessoa/{id}", existingId)
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.name").exists());
        result.andExpect(jsonPath("$.email").exists());
        result.andExpect(jsonPath("$.birthDate").exists());
    }

    @Test
    public void findByIdShouldReturnNotFoundWhenDoesNotExists() throws Exception {
        ResultActions result = mockMvc.perform(get("/pessoa/{id}", nonExistingId)
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isNotFound());
    }


    @Test
    public void findAllShouldReturnPage() throws Exception {
        ResultActions result = mockMvc.perform(get("/pessoa")
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk());
    }

    @Test
    public void insertShouldReturnPersonDTOCreated() throws Exception {
        String jsonBody = mapper.writeValueAsString(personDTO);
        ResultActions result = mockMvc.perform(post("/pessoa")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.name").exists());
        result.andExpect(jsonPath("$.email").exists());
        result.andExpect(jsonPath("$.birthDate").exists());
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void insertShouldThrowMethodArgumentNotValidExceptionWhenInvalidName(String input) throws Exception {
        PersonDTO personDTO1 = Factory.createPersonCustomized(input, "maria@gmail.com", LocalDate.ofEpochDay(2000 - 10 - 05));
        String jsonBody = mapper.writeValueAsString(personDTO1);
        ResultActions result = mockMvc.perform(post("/pessoa")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isUnprocessableEntity());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"mariagmail.com", "joao.br", "cachorro"})
    public void insertShouldThrowMethodArgumentNotValidExceptionWhenInvalidEmail(String input) throws Exception {
        PersonDTO personDTO1 = Factory.createPersonCustomized("Maria", input, LocalDate.ofEpochDay(2000 - 10 - 05));
        String jsonBody = mapper.writeValueAsString(personDTO1);
        ResultActions result = mockMvc.perform(post("/pessoa")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void updateShouldReturnProductDTOWhenIdExists() throws Exception {
        String jsonBody = mapper.writeValueAsString(personDTO);
        ResultActions result = mockMvc.perform(put("/pessoa/{id}", existingId)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.name").exists());
        result.andExpect(jsonPath("$.email").exists());
        result.andExpect(jsonPath("$.birthDate").exists());
    }

    @Test
    public void updateShouldNotReturnPersonDTOWhenIdDoesNotExists() throws Exception {
        String jsonBody = mapper.writeValueAsString(personDTO);
        ResultActions result = mockMvc.perform(put("/pessoa/{id}", nonExistingId)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isNotFound());
    }

    @Test
    public void deleteShouldReturnNoContentWhenIdExists() throws Exception {
        ResultActions result = mockMvc.perform(delete("/pessoa/{id}", existingId)
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isNoContent());
    }

    @Test
    public void deleteShouldReturnNotResourceNotFoundWhenIdExists() throws Exception {
        ResultActions result = mockMvc.perform(delete("/pessoa/{id}", nonExistingId)
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isNotFound());
    }

}
