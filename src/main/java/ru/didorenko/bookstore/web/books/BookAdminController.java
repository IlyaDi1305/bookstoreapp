package ru.didorenko.bookstore.web.books;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.didorenko.bookstore.model.Book;
import ru.didorenko.bookstore.repository.BookRepository;
import ru.didorenko.bookstore.service.BookService;
import java.net.URI;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.didorenko.bookstore.common.validation.ValidationUtil.assureIdConsistent;
import static ru.didorenko.bookstore.common.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = BookAdminController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class BookAdminController {
    @Autowired
    private BookService bookService;

    static final String REST_URL = "/api/books";

    protected final Logger log = getLogger(getClass());

    @Autowired
    private BookRepository repository;

    @GetMapping
    public ResponseEntity<Page<Book>> getBooks(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "brand", required = false) String brand,
            @RequestParam(value = "year", required = false) Integer year,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Book> books = bookService.findBooks(title, brand, year, pageable);
        return ResponseEntity.ok(books);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("Delete book with id={}", id);
        repository.deleteById(id);
    }

    @PostMapping
    public ResponseEntity<Book> createWithLocation(@Valid @RequestBody Book book) {
        log.info("create {}", book);
        checkNew(book);
        Book created = repository.prepareAndSave(book);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Book book, @PathVariable int id) {
        log.info("update {} with id={}", book, id);
        assureIdConsistent(book, id);
        repository.prepareAndSave(book);
    }
}