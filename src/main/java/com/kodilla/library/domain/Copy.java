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
@Table(name = "COPIES")
public class Copy {

    @Id
    @GeneratedValue
    private Long copiesId;

    @NotNull
    private String status;

    @ManyToOne
    @JoinColumn(name = "BOOK_ID")
    private Book book;

    @OneToMany(
            targetEntity = Borrow.class,
            mappedBy = "copy",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )

    private List<Borrow> borrows = new ArrayList<>();


}
