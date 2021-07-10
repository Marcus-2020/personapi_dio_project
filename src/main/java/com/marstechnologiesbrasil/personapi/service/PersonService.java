package com.marstechnologiesbrasil.personapi.service;

import com.marstechnologiesbrasil.personapi.dto.request.PersonDTO;
import com.marstechnologiesbrasil.personapi.dto.response.MessageResponseDTO;
import com.marstechnologiesbrasil.personapi.entity.Person;
import com.marstechnologiesbrasil.personapi.exception.PersonNotFoundException;
import com.marstechnologiesbrasil.personapi.mapper.PersonMapper;
import com.marstechnologiesbrasil.personapi.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService {

    private final PersonMapper personMapper = PersonMapper.INSTANCE;
    private PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public MessageResponseDTO createPerson(PersonDTO personDTO) {
        Person personToSave = personMapper.toModel(personDTO);

        Person savedPerson = personRepository.save(personToSave);
        return buildMessageResponseDTO(savedPerson.getId(), "Created person with ID ");
    }

    public List<PersonDTO> listAll() {
        List<Person> allPeople = personRepository.findAll();
        return allPeople.stream()
        .map(personMapper::toDTO)
        .collect(Collectors.toList());
    }

    public PersonDTO findById(Long id) throws PersonNotFoundException {
        Person person = verifyIfPersonExists(id);

        return personMapper.toDTO(person);
    }

    public MessageResponseDTO updateById(Long id, PersonDTO personDTO) throws PersonNotFoundException {
        verifyIfPersonExists(id);

        Person personToUpdate = personMapper.toModel(personDTO);

        Person personUpdated = personRepository.save(personToUpdate);

        return buildMessageResponseDTO(personUpdated.getId(), "Updated person with ID ");
    }

    public void deleteById(Long id) throws PersonNotFoundException {
        verifyIfPersonExists(id);

        personRepository.deleteById(id);
    }

    private MessageResponseDTO buildMessageResponseDTO(Long id, String s) {
        return MessageResponseDTO
                .builder()
                .message(s + id)
                .build();
    }

    private Person verifyIfPersonExists(Long id) throws PersonNotFoundException {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));
        return person;
    }
}
