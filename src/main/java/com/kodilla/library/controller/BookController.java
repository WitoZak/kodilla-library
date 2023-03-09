package com.kodilla.library.controller;

import com.kodilla.library.exeptions.BookAlreadyExistsException;
import com.kodilla.library.exeptions.BookNotFoundException;
import com.kodilla.library.exeptions.BookTitleAlreadyExistsException;
import com.kodilla.library.exeptions.BookTitleNotFoundException;
import com.kodilla.library.domain.Book;
import com.kodilla.library.domain.BookDto;
import com.kodilla.library.mapper.BookMapper;
import com.kodilla.library.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/library/books")
public class BookController {

    private final BookService bookService;
    private final BookMapper bookMapper;

    @GetMapping
    public ResponseEntity<List<BookDto>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        List<BookDto> bookDtos = bookMapper.mapToBookDtoList(books);
        return ResponseEntity.ok(bookDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable Long id) throws BookNotFoundException {
        Book book = bookService.getBookById(id);
        BookDto bookDto = bookMapper.mapToBookDto(book);
        return ResponseEntity.ok(bookDto);
    }

    @GetMapping("/title")
    public ResponseEntity<List<BookDto>> findByTitle(@RequestParam String title) throws BookTitleAlreadyExistsException {
        List<Book> books = bookService.findBookByTitle(title);
        List<BookDto> bookDtos = bookMapper.mapToBookDtoList(books);
        return ResponseEntity.ok(bookDtos);
    }

    @PostMapping
    public ResponseEntity<BookDto> addBook(@Valid @RequestBody BookDto bookDto) throws BookTitleNotFoundException,
            BookAlreadyExistsException, BookNotFoundException {
        Book book = bookMapper.mapToBook(bookDto);
        Book savedBook = bookService.save(book);
        BookDto savedBookDto = bookMapper.mapToBookDto(savedBook);
        return new ResponseEntity<>(savedBookDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookById(@PathVariable Long id) throws BookNotFoundException {
        bookService.deleteBookById(id);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book bookToUpdate)  throws BookTitleNotFoundException, BookNotFoundException {
        Book existingBook = bookService.getBookById(id);
        if (existingBook != null) {
            existingBook.setTitle(bookToUpdate.getTitle());
            existingBook.setAuthor(bookToUpdate.getAuthor());
            existingBook.setPublished(bookToUpdate.getPublished());
            Book updatedBook = bookService.save(existingBook);
            return ResponseEntity.ok(updatedBook);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

