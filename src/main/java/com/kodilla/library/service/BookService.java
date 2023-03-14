package com.kodilla.library.service;

import com.kodilla.library.domain.BookDto;
import com.kodilla.library.exeptions.BookAlreadyExistsException;
import com.kodilla.library.exeptions.BookNotFoundException;
import com.kodilla.library.domain.Book;
import com.kodilla.library.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    @Autowired
    public final BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book save(Book book) throws BookAlreadyExistsException {
        return bookRepository.save(book);

    }

    public Book getBookById(final Long id) {
        return bookRepository.findById(id).orElseThrow();

    }

    public Book updateBook(Long id, BookDto bookDto) throws BookNotFoundException {
        Book existingBook = getBookById(id);
        existingBook.setTitle(bookDto.getTitle());
        existingBook.setAuthor(bookDto.getAuthor());
        existingBook.setPublished(bookDto.getPublished());
        return save(existingBook);
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
