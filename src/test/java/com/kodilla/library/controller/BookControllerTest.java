package com.kodilla.library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kodilla.library.domain.Book;
import com.kodilla.library.domain.BookDto;
import com.kodilla.library.exeptions.BookNotFoundException;
import com.kodilla.library.exeptions.BookTitleAlreadyExistsException;
import com.kodilla.library.exeptions.BookTitleNotFoundException;
import com.kodilla.library.mapper.BookMapper;
import com.kodilla.library.repository.BookRepository;
import com.kodilla.library.service.BookService;
import org.apache.catalina.connector.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import os.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.jboss.logging.NDC.get;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    @Autowired
    private BookController bookController;
    @Autowired
    private BookRepository bookRepository;
    @Mock
    private BookMapper bookMapper;
    @Mock
    private BookService bookService;
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();

    }

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

    @Test
    void givenBookId_whenGetBookById_thenReturnBookDto() throws Exception {
        //given
        ObjectMapper objectMapper = new ObjectMapper();

        Long bookId = 1L;
        Book book = new Book(bookId, "Test Book", "Test Author", "22-02-22");
        BookDto expectedBookDto = new BookDto(bookId, "Test Book", "Test Author", "22-02-22");

        given(bookService.getBookById(bookId)).willReturn(book);
        given(bookMapper.mapToBookDto(book)).willReturn(expectedBookDto);

        //when
        MvcResult mvcResult = mockMvc.perform(get("/v1/library/books/" + bookId))
                .andExpect(status().isOk())
                .andReturn();

        //then
        BookDto actualBookDto = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                BookDto.class
        );

        assertThat(actualBookDto).isEqualTo(expectedBookDto);
    }

    @Test
    void givenNonExistingBookId_whenGetBookById_thenThrowBookNotFoundException() throws Exception {
        // given
        Long bookId = 1L;

        given(bookService.getBookById(bookId)).willThrow(new BookNotFoundException());

        // when
        mockMvc.perform(get("/v1/library/books/" + bookId))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BookNotFoundException));
    }

    @Test
    void givenInvalidBookIdFormat_whenGetBookById_thenThrowMethodArgumentTypeMismatchException() throws Exception {
        // given
        String invalidBookId = "invalid_id";

        // when
        mockMvc.perform(get("/v1/library/books/" + invalidBookId))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentTypeMismatchException));
    }

    @Test
    void testFindByTitleSuccess() throws BookTitleAlreadyExistsException {
        // given
        String title = "The Lord of the Rings";
        List<Book> books = Arrays.asList(
                new Book(1L, title, "J.R.R. Tolkien", "1954"),
                new Book(2L, title, "J.R.R. Tolkien", "1955")
        );
        given(bookService.findBookByTitle(title)).willReturn(books);
        given(bookMapper.mapToBookDtoList(books)).willReturn(
                Arrays.asList(
                        new BookDto(1L, title, "J.R.R. Tolkien", "1954"),
                        new BookDto(2L, title, "J.R.R. Tolkien", "1955")
                )
        );

        // when
        ResponseEntity<List<BookDto>> response = bookController.findByTitle(title);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(2);
        assertThat(response.getBody().get(0).getTitle()).isEqualTo(title);
        assertThat(response.getBody().get(0).getAuthor()).isEqualTo("J.R.R. Tolkien");
        assertThat(response.getBody().get(0).getPublished()).isEqualTo("1954");
        assertThat(response.getBody().get(1).getTitle()).isEqualTo(title);
        assertThat(response.getBody().get(1).getAuthor()).isEqualTo("J.R.R. Tolkien");
        assertThat(response.getBody().get(1).getPublished()).isEqualTo("1955");
    }

    @Test
    void testFindByTitleNotFound() throws BookTitleAlreadyExistsException {
        // given
        String title = "The Lord of the Rings";
        given(bookService.findBookByTitle(title)).willReturn(Collections.emptyList());

        // when
        ResponseEntity<List<BookDto>> response = bookController.findByTitle(title);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEmpty();
    }

    @Test
    void addBook_withValidData_returnsCorrectResponse() throws Exception {
        //given
        BookDto bookDto = new BookDto(null, "Test Book", "Test Author", "2022-01-01");

        //when
        when(bookService.save(any(Book.class))).thenAnswer((Answer<Book>) invocation -> invocation.getArgument(0));
        String json = new ObjectMapper().writeValueAsString(bookDto);
        MvcResult mvcResult = mockMvc.perform(post("/v1/library/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andReturn();
        String responseJson = mvcResult.getResponse().getContentAsString();
        BookDto savedBookDto = new ObjectMapper().readValue(responseJson, BookDto.class);

        // then
        assertEquals(bookDto.getTitle(), savedBookDto.getTitle());
        assertEquals(bookDto.getAuthor(), savedBookDto.getAuthor());
        assertEquals(bookDto.getPublished(), savedBookDto.getPublished());
        assertNotNull(savedBookDto.getBookId());
    }

    @Test
    void addBook_BookTitleAlreadyExists_ShouldReturnBadRequest() throws Exception {
        //given
        ObjectMapper objectMapper = new ObjectMapper();
        BookDto bookDto = new BookDto();
        bookDto.setTitle("Sample Title");
        bookDto.setAuthor("Sample Author");
        bookDto.setPublished("2022-01-01");

        //when
        BookTitleAlreadyExistsException exception = new BookTitleAlreadyExistsException();
        when(bookService.save(any(Book.class))).thenThrow(exception);

        // then
        mockMvc.perform(post("/v1/library/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testAddBookWithNonExistingTitle() {
        //given
        BookDto bookDto = new BookDto();
        bookDto.setTitle("Non-existing Title");
        bookDto.setAuthor("John Doe");

        //when
        when(bookRepository.findByTitle(anyString())).thenReturn(Optional.empty()); // mock the repository method to return an empty optional

        ResponseEntity<BookDto> response = bookController.addBook(bookDto);

        // then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void addBook_withInvalidData_returnsBadRequest() throws Exception {
        // given
        ObjectMapper objectMapper = new ObjectMapper();
        BookDto invalidBook = new BookDto(null, "", "", "");
        String requestBody = objectMapper.writeValueAsString(invalidBook);

        // when
        MvcResult mvcResult = mockMvc.perform(post("/v1/library/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andReturn();

        // then
        String errorMessage = mvcResult.getResponse().getContentAsString();
        assertThat(errorMessage).isEqualTo("Invalid book data");
    }

    @Test
    void givenValidBookId_whenDeleteBookById_then204NoContent() {
        // given
        Long bookId = 1L;
        Book book = new Book(bookId, "Title", "Author", "2020-02-02", "1234567890");
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        // when
        ResponseEntity<Void> response = bookController.deleteBookById(bookId);

        // then
        verify(bookRepository, times(1)).deleteById(bookId);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void givenInvalidBookId_whenDeleteBookById_then404NotFound() {
        // given
        Long bookId = 1L;
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        // when
        ResponseEntity<Void> response = bookController.deleteBookById(bookId);

        // then
        verify(bookRepository, times(0)).deleteById(bookId);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void givenNullBookId_whenDeleteBookById_then400BadRequest() {
        // given
        Long bookId = null;

        // when
        ResponseEntity<Void> response = bookController.deleteBookById(bookId);

        // then
        verify(bookRepository, times(0)).deleteById(anyLong());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testUpdateBookWithValidIdAndBook() throws BookTitleNotFoundException, BookNotFoundException {
        // given
        Long bookId = 1L;
        Book existingBook = new Book(bookId, "Old title", "Old author", "Old published");
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(existingBook));

        BookDto bookToUpdate = new BookDto(bookId, "New title", "New author", "New published");

        // when
        Book updatedBook = bookService.updateBook(bookId, bookToUpdate);

        // then
        assertNotNull(updatedBook);
        assertEquals(bookId, updatedBook.getBookId());
        assertEquals(bookToUpdate.getTitle(), updatedBook.getTitle());
        assertEquals(bookToUpdate.getAuthor(), updatedBook.getAuthor());
        assertEquals(bookToUpdate.getPublished(), updatedBook.getPublished());
    }





    @Test(expected = ResourceNotFoundException.class)
    void testUpdateBookWithInvalidId() {
        //given
        Long id = 1L;
        BookDto updatedBook = new BookDto(id, "Java Programming 2.0", "John Doe", "2022-02-02");
        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        //when
        bookService.updateBook(id, updatedBook);

        //then
        verify(bookRepository).findById(id);
        verify(bookRepository, never()).save(any());
    }

    @Test(expected = IllegalArgumentException.class)
    void testUpdateBookWithNullBook() {
        //given
        Long id = 1L;

        //when
        bookService.updateBook(id, null);

        //then
        verify(bookRepository, never()).findById(any());
        verify(bookRepository, never()).save(any());
    }

    @Test(expected = IllegalArgumentException.class)
    void testUpdateBookWithNullId() {
        //given
        BookDto updatedBook = new BookDto(1L, "Java Programming 2.0", "John Doe", "2022-02-02");

        //when
        bookService.updateBook(null, updatedBook);

        //then
        verify(bookRepository, never()).findById(any());
        verify(bookRepository, never()).save(any());
    }
}

