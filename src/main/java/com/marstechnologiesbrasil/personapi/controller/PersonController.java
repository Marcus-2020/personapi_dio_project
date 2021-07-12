package com.marstechnologiesbrasil.personapi.controller;

import com.marstechnologiesbrasil.personapi.dto.request.PersonDTO;
import com.marstechnologiesbrasil.personapi.dto.response.MessageResponseDTO;
import com.marstechnologiesbrasil.personapi.exception.PersonNotFoundException;
import com.marstechnologiesbrasil.personapi.service.PersonService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/people")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PersonController {

    private PersonService personService;

    /**
     * Create a new person in the repository/database.
     * @param personDTO A PersonDTO object to be created in the repository/database.
     * @return A message confirming if the creation was successful.
     */
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Create a person with a generated id"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponseDTO createPerson(@RequestBody @Valid PersonDTO personDTO) {
        return personService.createPerson(personDTO);
    }

    /**
     * Gets a list of all the persons registered in the database in that moment.
     * @return A list of all the persons registered in the repository/database in that moment.
     */
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return the list of persons in the repository/database"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<PersonDTO> listAll() {
        return personService.listAll();
    }

    /**
     * Get a person from the database with a given ID.
     * @param id An unique identifier for the person been sought.
     * @return A PersonDTO instance of the person in repository/database with the passed ID.
     * @throws PersonNotFoundException Thrown when the ID passed don't exists in the repository/database.
     */
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return a persons from n the repository by a given ID"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public PersonDTO findById(@PathVariable Long id) throws PersonNotFoundException {
        return personService.findById(id);
    }

    /**
     * Update a person from the database with a given ID.
     * @param id An unique identifier for the person been updated.
     * @param personDTO A PersonDTO instance to override the one in the repository/database.
     * @return A message confirming that the update was successful.
     * @throws PersonNotFoundException Thrown when the ID passed don't exists in the repository/database.
     */
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Person with the given ID has been successfully updated"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public MessageResponseDTO updateById(@PathVariable Long id, @RequestBody @Valid PersonDTO personDTO) throws PersonNotFoundException {
        return personService.updateById(id, personDTO);
    }

    /**
     * Delete a person from the repository/database with a given ID.
     * @param id An unique identifier for the person been deleted.
     * @throws PersonNotFoundException Thrown when the ID passed don't exists in the repository/database.
     */
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Persons with the given ID has been successfully deleted"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) throws PersonNotFoundException {
        personService.deleteById(id);
    }
}
