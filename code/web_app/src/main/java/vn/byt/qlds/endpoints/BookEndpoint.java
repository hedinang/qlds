package vn.byt.qlds.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import vn.byt.qlds.exception.NotFoundException;
import vn.byt.qlds.jwt.JwtUserDetails;
import vn.byt.qlds.model.book.Book;
import vn.byt.qlds.model.book.BookRequest;
import vn.byt.qlds.repository.BookRepository;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@RestController
@RequestMapping("/books")
public class BookEndpoint {

    @Autowired
    private BookRepository repository;

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN','CRUD','WRITE','WRITE_BOOK')")
    public Book createBook(@AuthenticationPrincipal JwtUserDetails userDetails, @NotNull @Valid @RequestBody final BookRequest request) {
        Book book = new Book(repository.size() + 1L, request.title, request.author);
        repository.add(book);
        return book;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CRUD','READ','READ_BOOK')")
    public Book findById(@PathVariable Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Not found book id " + id));
    }

    @GetMapping("/")
    @PreAuthorize("hasAnyAuthority('ADMIN','CRUD','READ','READ_BOOK')")
    public Collection<Book> findBooks() {
        return repository.getBooks();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN','CRUD','CREAD','WRITE_BOOK')")
    public Book updateBook(@PathVariable("id") final String id, @RequestBody final Book book) {
        return book;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN','CRUD','DELETE','DELETE_BOOK')")
    public long deleteBook(@PathVariable final long id) {
        return id;
    }
}