package br.com.coursespring.services;

import br.com.coursespring.Book;
import br.com.coursespring.controllers.BookController;
import br.com.coursespring.data.vo.v1.BookVO;
import br.com.coursespring.exceptions.RequiredObjectIsNullException;
import br.com.coursespring.exceptions.ResourceNotFoundException;
import br.com.coursespring.mappers.GenericMapper;
import br.com.coursespring.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    private final Logger logger = Logger.getLogger(BookService.class.getName());

    public BookVO findById(Long id) {

        logger.info("Finding one book!");

        Book entity = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        BookVO vo = GenericMapper.parseObject(entity, BookVO.class);

        vo.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());

        return vo;
    }

    public List<BookVO> findAll() {

        logger.info("Finding all books!");

        List<Book> people = bookRepository.findAll();

        List<BookVO> peopleVO = GenericMapper.parseListObjects(people, BookVO.class);

        peopleVO.forEach((bookVO) ->
                bookVO.add(linkTo(methodOn(BookController.class).findById(bookVO.getKey())).withSelfRel()));


        return peopleVO;
    }

    public BookVO create(BookVO bookVO) {
        logger.info("Creating one book");

        if(bookVO == null) throw new RequiredObjectIsNullException();

        Book book = GenericMapper.parseObject(bookVO, Book.class);

        BookVO bookVOMapped = GenericMapper.parseObject(bookRepository.save(book), BookVO.class);

        bookVOMapped.add(linkTo(methodOn(BookController.class).findById(bookVOMapped.getKey())).withSelfRel());

        return bookVOMapped;
    }

    public BookVO update(BookVO bookVO) {
        logger.info("Updating one book");

        if(bookVO == null) throw new RequiredObjectIsNullException();

        BookVO bookVOFound = findById(bookVO.getKey());
        bookVOFound.setAuthor(bookVO.getAuthor());
        bookVOFound.setTitle(bookVO.getTitle());
        bookVOFound.setPrice(bookVO.getPrice());
        bookVOFound.setLaunchDate(bookVO.getLaunchDate());

        Book book = GenericMapper.parseObject(bookVOFound, Book.class);

        BookVO bookVOMapped = GenericMapper.parseObject(bookRepository.save(book), BookVO.class);

        bookVOMapped.add(linkTo(methodOn(BookController.class).findById(bookVOMapped.getKey())).withSelfRel());

        return  bookVOMapped;
    }

    public void delete(Long id) {
        logger.info("Deleting one book");

        BookVO bookVO = findById(id);
        bookRepository.delete(GenericMapper.parseObject(bookVO, Book.class));
    }
}
