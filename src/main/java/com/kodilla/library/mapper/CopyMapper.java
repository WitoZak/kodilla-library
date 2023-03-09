package com.kodilla.library.mapper;

import com.kodilla.library.domain.Book;
import com.kodilla.library.domain.Copy;
import com.kodilla.library.domain.CopyDto;
import com.kodilla.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CopyMapper {

    @Autowired
    BookService bookService;

    public Copy mapToCopy(final CopyDto copyDto) {
        Copy copy = new Copy();
        copy.setCopiesId(copyDto.getCopiesId());
        copy.setStatus(copyDto.getStatus());
        Book book = new Book();
        book.setBookId(bookService.getBookById(copyDto.getBookId()).getBookId());
        book.setAuthor(bookService.getBookById(copyDto.getBookId()).getAuthor());
        book.setPublished(bookService.getBookById(copyDto.getBookId()).getPublished());
        copy.setBook(book);
        return copy;
    }

    public CopyDto mapToCopyDto(final Copy copy) {
        return new CopyDto(
                copy.getCopiesId(),
                copy.getStatus(),
                copy.getBook().getBookId());
    }

    public List<CopyDto> mapToCopyDtoList(List<Copy> itemList) {
        return itemList.stream()
                .map(this::mapToCopyDto)
                .toList();
    }
}
