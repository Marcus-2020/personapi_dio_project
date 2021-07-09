package com.marstechnologiesbrasil.personapi.repository;

import com.marstechnologiesbrasil.personapi.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {

}
