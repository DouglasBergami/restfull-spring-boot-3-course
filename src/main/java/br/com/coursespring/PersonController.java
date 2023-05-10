package br.com.coursespring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping()
    public List<Person> findAll() {
        return personService.findAll();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Person findById(@PathVariable(value = "id") Long id) {
        return personService.findById(id);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Person create(@RequestBody() Person person) {
        return personService.create(person);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Person update(@RequestBody() Person person) {
        return personService.update(person);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus()
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        personService.delete(id);

        return ResponseEntity.noContent().build();
    }
}