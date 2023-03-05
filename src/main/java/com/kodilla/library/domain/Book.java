package com.kodilla.library.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@RequiredArgsConstructor
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
    private int published;

    @ManyToMany
    private Copy copy;

    @Column(name = "DELETED")
    private boolean deleted = false;
}
