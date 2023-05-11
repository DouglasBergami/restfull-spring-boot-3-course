package br.com.coursespring.controllers;

import br.com.coursespring.data.vo.v1.PersonVO;
import br.com.coursespring.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/person/v1")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping()
    public List<PersonVO> findAll() {
        return personService.findAll();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PersonVO findById(@PathVariable(value = "id") Long id) {
        return personService.findById(id);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public PersonVO create(@RequestBody() PersonVO person) {
        return personService.create(person);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public PersonVO update(@RequestBody() PersonVO person) {
        return personService.update(person);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus()
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        personService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
