package com.kodilla.library.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "BORROW_PROSESS")
public class BorrowProcess {

    @Id
    @GeneratedValue
    private Long borrowProcessId;

    @ManyToOne
    @JoinColumn(name = "READER_ID")
    private Reader reader;

    @Column(name = "BOOK_ID")
    private String bookTitle;

    @Column(name = "RETURN_DATE")
    @JsonFormat(pattern = "yyyy-mm-dd")
    private LocalDate returnDate;

    @Column (name = "BORROW_DATE")
    @JsonFormat (pattern = "yyyy-mm-dd")
    private LocalDate borrowDate;

}
