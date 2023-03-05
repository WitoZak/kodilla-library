package com.kodilla.library.service;

import com.kodilla.library.Exeptions.BookAlreadyExistsException;
import com.kodilla.library.Exeptions.BookNotFoundException;
import com.kodilla.library.domain.Book;
import com.kodilla.library.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return (List<Book>) bookRepository.findAll();
    }

    public Book save(Book book) throws BookAlreadyExistsException {
        return bookRepository.save(book);

    }

    public Book getBookId(final Long id) {
        return bookRepository.findById(id)
                .filter(t -> !t.isDeleted())
                .orElseThrow(BookNotFoundException::new);
    }

    public void deleteBookById(Long id) throws BookNotFoundException {
        Book book = bookRepository.findById(id)
                .orElseThrow(BookNotFoundException::new);
        bookRepository.deleteById(id);
        bookRepository.save(book);
    }

    public List<Book> findBookByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

}
