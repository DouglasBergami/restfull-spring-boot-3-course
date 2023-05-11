package br.com.coursespring.services;

import br.com.coursespring.Person;
import br.com.coursespring.data.vo.v1.PersonVO;
import br.com.coursespring.exceptions.ResourceNotFoundException;
import br.com.coursespring.mappers.GenericMapper;
import br.com.coursespring.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.Logger;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    private final Logger logger = Logger.getLogger(PersonService.class.getName());

    public PersonVO findById(Long id) {

        logger.info("Finding one person!");

        Person etity = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        return GenericMapper.parseObject(etity, PersonVO.class);
    }

    public List<PersonVO> findAll() {

        logger.info("Finding all people!");

        List<Person> people = personRepository.findAll();

        return GenericMapper.parseListObjects(people, PersonVO.class);
    }

    public PersonVO create(PersonVO personVO) {
        logger.info("Creating one person");

        Person person = GenericMapper.parseObject(personVO, Person.class);

        return GenericMapper.parseObject(personRepository.save(person), PersonVO.class);
    }

    public PersonVO update(PersonVO personVO) {
        logger.info("Updating one person");

        PersonVO personVOFound = findById(personVO.getId());
        personVOFound.setFirstName(personVO.getFirstName());
        personVOFound.setLastName(personVO.getLastName());
        personVOFound.setAddress(personVO.getAddress());
        personVOFound.setGender(personVO.getGender());

        Person person = GenericMapper.parseObject(personVOFound, Person.class);

        return  GenericMapper.parseObject(personRepository.save(person), PersonVO.class);
    }

    public void delete(Long id) {
        logger.info("Deleting one person");

        PersonVO personVO = findById(id);
        personRepository.delete(GenericMapper.parseObject(personVO, Person.class));
    }
}
