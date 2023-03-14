package com.kodilla.library.domain;

import lombok.*;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
//@Builder
//@Value
public class BookDto {

    private Long bookId;
    private String title;
    private String author;
    private String published;

    public BookDto(String title, String author, String published) {
        this.title = title;
        this.author = author;
        this.published = published;
    }
}
