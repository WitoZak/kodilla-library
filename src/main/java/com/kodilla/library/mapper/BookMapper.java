package com.kodilla.library.mapper;

import com.kodilla.library.domain.Book;
import com.kodilla.library.domain.BookDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookMapper {

    public Book mapToBook(final BookDto bookDto) {
        Book book = new Book();
        bookDto.getBookId();
        bookDto.getTitle();
        bookDto.getAuthor();
        bookDto.getPublished();
        return book;
    }

    public BookDto mapToBookDto(final Book book) {
        return new BookDto(
                book.getBookId(),
                book.getTitle(),
                book.getAuthor(),
                book.getPublished());
    }

    public List<BookDto> mapToBookDtoList(final List<Book> bookList) {
        return bookList.stream()
                .map(this::mapToBookDto)
                .toList();
    }
}
