package com.marstechnologiesbrasil.personapi.utils;

import com.marstechnologiesbrasil.personapi.dto.request.PersonDTO;
import com.marstechnologiesbrasil.personapi.entity.Person;

import java.time.LocalDate;
import java.util.Collections;

public class PersonUtils {

    private static final String FIRST_NAME = "Mark";
    private static final String LAST_NAME = "Ashton";
    private static final String CPF_NUMBER = "469.374.748-30";
    private static final long PERSON_ID = 1L;
    private static final LocalDate BIRTH_DATE = LocalDate.of(2000, 5, 13);

    public static PersonDTO createFakeDTO() {
        return PersonDTO.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .cpf(CPF_NUMBER)
                .birthDate("13-05-2000")
                .phones(Collections.singletonList(PhoneUtils.createFakeDTO()))
                .build();
    }

    public static Person createFakeEntity() {
        return Person.builder()
                .id(PERSON_ID)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .cpf(CPF_NUMBER)
                .birthDate(BIRTH_DATE)
                .phones(Collections.singletonList(PhoneUtils.createFakeEntity()))
                .build();
    }
}
