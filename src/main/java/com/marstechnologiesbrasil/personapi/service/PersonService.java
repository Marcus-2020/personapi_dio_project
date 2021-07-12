package com.marstechnologiesbrasil.personapi.service;

import com.marstechnologiesbrasil.personapi.dto.request.PersonDTO;
import com.marstechnologiesbrasil.personapi.dto.response.MessageResponseDTO;
import com.marstechnologiesbrasil.personapi.entity.Person;
import com.marstechnologiesbrasil.personapi.exception.PersonNotFoundException;
import com.marstechnologiesbrasil.personapi.mapper.PersonMapper;
import com.marstechnologiesbrasil.personapi.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PersonService {

    /**
     * MapStruct mapper used to parse PersonDTO to Person class and vice versa.
     */
    private final PersonMapper personMapper = PersonMapper.INSTANCE;

    /**
     * The repository of Persons that executes the data access operations to the database.
     */
    private PersonRepository personRepository;

    /**
     * Saves a new Person in the repository from a PersonDTO instance.
     * @param personDTO A PersonDTO instance to be saved in the repository/database.
     * @return A message confirming the Person creation.
     */
    public MessageResponseDTO createPerson(PersonDTO personDTO) {
        // Parse the PersonDTO passed to a Person instance to be saved in the repository/database.
        Person personToSave = personMapper.toModel(personDTO);

        // Saves the Person to save in the repository/database.
        Person savedPerson = personRepository.save(personToSave);
        return buildMessageResponseDTO(savedPerson.getId(), "Created person with ID ");
    }

    /**
     * Gets all Persons in the repository/database.
     * @return A list of all Persons in the repository/database.
     */
    public List<PersonDTO> listAll() {
        List<Person> allPeople = personRepository.findAll();
        return allPeople.stream()
        .map(personMapper::toDTO)
        .collect(Collectors.toList());
    }

    /**
     * Get a Person from the the repository/database with a given ID.
     * @param id An unique identifier for the person been sought.
     * @return A PersonDTO instance of the Person in the repository/database.
     * @throws PersonNotFoundException Thrown when the ID passed don't exists in the database.
     */
    public PersonDTO findById(Long id) throws PersonNotFoundException {
        // Verify if the person exists in the repository/database, if does store in the variable.
        Person person = verifyIfPersonExists(id);

        // Return a parsed Person
        return personMapper.toDTO(person);
    }

    /**
     * Update a Person in the repository/database with a given ID.
     * @param id An unique identifier for the person been updated.
     * @param personDTO A PersonDTO object to override the one in the database.
     * @return A message confirming if the update was successful.
     * @throws PersonNotFoundException Thrown when the ID passed don't exists in the database
     */
    public MessageResponseDTO updateById(Long id, PersonDTO personDTO) throws PersonNotFoundException {
        // Verify if the person exists in the repository/database.
        verifyIfPersonExists(id);

        // Parse the PersonDTO instance to Person to be updated
        Person personToUpdate = personMapper.toModel(personDTO);

        // Update the person in the repository/database
        Person personUpdated = personRepository.save(personToUpdate);

        // Return the success message
        return buildMessageResponseDTO(personUpdated.getId(), "Updated person with ID ");
    }

    /**
     * Delete a person from the repository/database with a given ID.
     * @param id An unique identifier for the person been deleted.
     * @throws PersonNotFoundException Thrown when the ID passed don't exists in the database.
     */
    public void deleteById(Long id) throws PersonNotFoundException {
        // Verify if the person exists in the repository/database.
        verifyIfPersonExists(id);

        //If exists, then delete it
        personRepository.deleteById(id);
    }

    /**
     * Create a new MessageResponseDTO to be returned from a service method.
     * @param id An unique identifier for the person been saved, sought, altered or deleted.
     * @param s The message to be returned by the method.
     * @return A MessageResponseDTo instance.
     */
    private MessageResponseDTO buildMessageResponseDTO(Long id, String s) {
        return MessageResponseDTO
                .builder()
                .message(s + id)
                .build();
    }

    /**
     * Verify if a person with a givcen ID exists in the repository/database.
     * @param id An unique identifier for the person.
     * @return A Person if exists in the repository/database.
     * @throws PersonNotFoundException Thrown when the ID passed don't exists in the database.
     */
    private Person verifyIfPersonExists(Long id) throws PersonNotFoundException {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));
        return person;
    }
}
