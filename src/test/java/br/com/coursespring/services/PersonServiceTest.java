package br.com.coursespring.services;

import br.com.coursespring.Person;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import br.com.coursespring.data.vo.v1.PersonVO;
import br.com.coursespring.repositories.PersonRepository;
import br.com.coursespring.services.mocks.MockPerson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    MockPerson input;

    @InjectMocks
    private PersonService service;

    @Mock
    private PersonRepository personRepository;

    @BeforeEach
    void setUpMocks() {
        input = new MockPerson();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void find_All() {
        List<Person> entityList = input.mockEntityList();
        List<PersonVO> personVOList = input.mockVOList();

        when(personRepository.findAll()).thenReturn(entityList);

        var results = service.findAll();

        verify(personRepository, times(1)).findAll();

        for(int i=0; i<results.size(); i++) {
            var link = results.get(i).getLinks().stream().findFirst().get();

            assertEquals(personVOList.get(i).getKey(), results.get(i).getKey());
            assertEquals(personVOList.get(i) .getFirstName(), results.get(i).getFirstName());
            assertEquals(personVOList.get(i) .getLastName(), results.get(i).getLastName());
            assertEquals(personVOList.get(i) .getAddress(), results.get(i).getAddress());
            assertEquals(personVOList.get(i) .getGender(), results.get(i).getGender());
            assertEquals(link.getRel().value(), "self");
            assertEquals(link.getHref(), "/api/person/v1/" + i);
        }
    }

    @Test
    void find_by_id() {
        Person entity = input.mockEntity(1);
        PersonVO personVO = input.mockVO(1);

        when(personRepository.findById(1L)).thenReturn(Optional.of(entity));

        var result = service.findById(1L);
        var link = result.getLinks().stream().findFirst().get();

        verify(personRepository, times(1)).findById(anyLong());

        assertEquals(personVO.getKey(), result.getKey());
        assertEquals(personVO.getFirstName(), result.getFirstName());
        assertEquals(personVO.getLastName(), result.getLastName());
        assertEquals(personVO.getAddress(), result.getAddress());
        assertEquals(personVO.getGender(), result.getGender());
        assertEquals(link.getRel().value(), "self");
        assertEquals(link.getHref(), "/api/person/v1/1");
    }

    @Test
    void create() {
        Person entity = input.mockEntity(1);
        PersonVO personVO = input.mockVO(1);

        when(personRepository.save(entity)).thenReturn(entity);

        var result = service.create(personVO);
        var link = result.getLinks().stream().findFirst().get();

        verify(personRepository, times(1)).save(any(Person.class));

        assertEquals(personVO.getKey(), result.getKey());
        assertEquals(personVO.getFirstName(), result.getFirstName());
        assertEquals(personVO.getLastName(), result.getLastName());
        assertEquals(personVO.getAddress(), result.getAddress());
        assertEquals(personVO.getGender(), result.getGender());
        assertEquals(link.getRel().value(), "self");
        assertEquals(link.getHref(), "/api/person/v1/1");
    }

    @Test
    void update() {
        Person entity = input.mockEntity(1);
        PersonVO personVO = input.mockVO(1);
        personVO.setFirstName("mockNewName");

        when(personRepository.findById(1L)).thenReturn(Optional.of(entity));
        entity.setFirstName("mockNewName");
        when(personRepository.save(entity)).thenReturn(entity);

        var result = service.update(personVO);
        var link = result.getLinks().stream().findFirst().get();

        verify(personRepository, times(1)).save(any(Person.class));

        assertEquals(personVO.getKey(), result.getKey());
        assertEquals(personVO.getFirstName(), result.getFirstName());
        assertEquals(personVO.getLastName(), result.getLastName());
        assertEquals(personVO.getAddress(), result.getAddress());
        assertEquals(personVO.getGender(), result.getGender());
        assertEquals(link.getRel().value(), "self");
        assertEquals(link.getHref(), "/api/person/v1/1");
    }

    @Test
    void delete() {
        Person entity = input.mockEntity(1);

        when(personRepository.findById(1L)).thenReturn(Optional.of(entity));

        service.delete(1L);

        verify(personRepository, times(1)).delete(any(Person.class));
    }
}
