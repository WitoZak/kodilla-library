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

    @GetMapping("/{bookId}")
    public ResponseEntity<BookDto> getBookById(@PathVariable Long bookId) throws BookNotFoundException {
        Book book = bookService.getBookById(bookId);
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

    @DeleteMapping("/{bookId}")
    public ResponseEntity<Void> deleteBookById(@PathVariable Long bookId) throws BookNotFoundException {
        bookService.deleteBookById(bookId);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("/{bookId}")
    public ResponseEntity<Book> updateBook(@PathVariable Long bookId, @RequestBody Book bookToUpdate)  throws BookTitleNotFoundException, BookNotFoundException {
        Book existingBook = bookService.getBookById(bookId);
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

