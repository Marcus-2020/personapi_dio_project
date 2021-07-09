package com.marstechnologiesbrasil.personapi.controller;

import com.marstechnologiesbrasil.personapi.dto.MessageResponseDTO;
import com.marstechnologiesbrasil.personapi.entity.Person;
import com.marstechnologiesbrasil.personapi.service.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/people")
public class PersonController {

    private PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponseDTO createPerson(@RequestBody Person person) {
        return personService.createPerson(person);
    }
}
