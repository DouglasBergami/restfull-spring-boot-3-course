package br.com.coursespring;

import br.com.coursespring.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.Logger;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    private final Logger logger = Logger.getLogger(PersonService.class.getName());

    public Person findById(Long id) {

        logger.info("Finding one person!");

        Person etity = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        return etity;
    }

    public List<Person> findAll() {

        logger.info("Finding all people!");

        return personRepository.findAll();
    }

    public Person create(Person person) {
        logger.info("Creating one person");

        return personRepository.save(person);
    }

    public Person update(Person person) {
        logger.info("Updating one person");

        Person entity = findById(person.getId());
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        return personRepository.save(entity);
    }

    public void delete(Long id) {
        logger.info("Deleting one person");

        Person entity = findById(id);
        personRepository.delete(entity);
    }
}
