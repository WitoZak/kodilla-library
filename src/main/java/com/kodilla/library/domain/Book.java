package com.kodilla.library.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "BOOKS")
public class Book {

    @Id
    @GeneratedValue
    private Long bookId;

    @NotNull
    private String title;

    @NotNull
    private String author;

    @NotNull
    private String published;

    @OneToMany(
            targetEntity = Copy.class,
            mappedBy = "book",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<Copy> copyList = new ArrayList<>();

    public Book(Long bookId, String title, String author, String published) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.published = published;
    }
}
