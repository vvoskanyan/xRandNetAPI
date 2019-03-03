package com.ysu.xrandnet.repos;

import com.ysu.xrandnet.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Integer> {
    Person findById(int id);
}
