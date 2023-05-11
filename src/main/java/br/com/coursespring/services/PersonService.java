package br.com.coursespring.services;

import br.com.coursespring.Person;
import br.com.coursespring.controllers.PersonController;
import br.com.coursespring.data.vo.v1.PersonVO;
import br.com.coursespring.exceptions.ResourceNotFoundException;
import br.com.coursespring.mappers.GenericMapper;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
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

        Person entity = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        PersonVO vo = GenericMapper.parseObject(entity, PersonVO.class);

        vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());

        return vo;
    }

    public List<PersonVO> findAll() {

        logger.info("Finding all people!");

        List<Person> people = personRepository.findAll();

        List<PersonVO> peopleVO = GenericMapper.parseListObjects(people, PersonVO.class);

        peopleVO.forEach((personVO) ->
                personVO.add(linkTo(methodOn(PersonController.class).findById(personVO.getKey())).withSelfRel()));


        return peopleVO;
    }

    public PersonVO create(PersonVO personVO) {
        logger.info("Creating one person");

        Person person = GenericMapper.parseObject(personVO, Person.class);

        PersonVO personVOMapped = GenericMapper.parseObject(personRepository.save(person), PersonVO.class);

        personVOMapped.add(linkTo(methodOn(PersonController.class).findById(personVOMapped.getKey())).withSelfRel());

        return personVOMapped;
    }

    public PersonVO update(PersonVO personVO) {
        logger.info("Updating one person");

        PersonVO personVOFound = findById(personVO.getKey());
        personVOFound.setFirstName(personVO.getFirstName());
        personVOFound.setLastName(personVO.getLastName());
        personVOFound.setAddress(personVO.getAddress());
        personVOFound.setGender(personVO.getGender());

        Person person = GenericMapper.parseObject(personVOFound, Person.class);

        PersonVO personVOMapped = GenericMapper.parseObject(personRepository.save(person), PersonVO.class);

        personVOMapped.add(linkTo(methodOn(PersonController.class).findById(personVOMapped.getKey())).withSelfRel());

        return  personVOMapped;
    }

    public void delete(Long id) {
        logger.info("Deleting one person");

        PersonVO personVO = findById(id);
        personRepository.delete(GenericMapper.parseObject(personVO, Person.class));
    }
}
