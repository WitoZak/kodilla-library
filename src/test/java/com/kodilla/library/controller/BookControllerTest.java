package com.kodilla.library.controller;

import com.kodilla.library.domain.Book;
import com.kodilla.library.domain.BookDto;
import com.kodilla.library.mapper.BookMapper;
import com.kodilla.library.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc
class BookControllerTest {

    @Autowired
    private BookController bookController;
    @Mock
    private BookMapper bookMapper;
    @Mock
    private BookService bookService;

    @Test
    void testGetAllBooks() {
        //given
        List<BookDto> expectedBooks = List.of(
                new BookDto(1L, "Book1", "Author1", "2022-02-02"),
                new BookDto(2L, "Book2", "Author2", "2011-01-01")
        );
        List<Book> expectedBooksMapped = expectedBooks
                .stream()
                .map(bookMapper::mapToBook).toList();
        when(bookService.getAllBooks()).thenReturn(expectedBooksMapped);

        //when
        ResponseEntity<List<BookDto>> response = bookController.getAllBooks();

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(expectedBooks.size()).containsAll(expectedBooks);
    }

    @Test
    void testGetAllBooksIfListIsEmpty() {
        //given
        given(bookService.getAllBooks()).willReturn(new ArrayList<>());

        //when
        ResponseEntity<List<BookDto>> response = bookController.getAllBooks();

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEmpty();
    }

    @Test
    void getAllBooksThenReturnsListOfBooksDto() {

        //given
        List<Book> books = new ArrayList<>();
        Book book1 = new Book(1L, "Book1", "Author1", "2022-02-02");
        Book book2 = new Book(2L, "Book2", "Author2", "2011-01-01");
        books.add(book1);
        books.add(book2);
        List<BookDto> bookDtos = new ArrayList<>();
        BookDto bookDto1 = new BookDto(1L, "Book1", "Author1", "2022-02-02");
        BookDto bookDto2 = new BookDto(2L, "Book2", "Author2", "2011-01-01");
        bookDtos.add(bookDto1);
        bookDtos.add(bookDto2);
        when(bookService.getAllBooks()).thenReturn(books);
        when(bookMapper.mapToBookDtoList(books)).thenReturn(bookDtos);
        bookController = new BookController(bookService, bookMapper);

        //when
        ResponseEntity<List<BookDto>> response = bookController.getAllBooks();

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(2);
        assertThat(response.getBody().get(0)).isEqualTo(bookDto1);
        assertThat(response.getBody().get(1)).isEqualTo(bookDto2);
    }
}
