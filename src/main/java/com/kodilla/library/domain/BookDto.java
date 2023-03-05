package com.kodilla.library.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookDto {

    private Long bookId;
    private String title;
    private String author;
    private int published;
}
