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
@Table(name = "BORROWS")
public class Borrow {
    @Id
    @GeneratedValue
    private Long borrowId;

    @Column (name = "BORROW_DATE")
    @JsonFormat (pattern = "yyyy-mm-dd")
    private LocalDate borrowDate;

    @Column (name = "RETURN_DATE")
    @JsonFormat (pattern = "yyyy-mm-dd")
    private LocalDate returnDate;

    @ManyToOne
    @JoinColumn (name = "READER_ID")
    private Reader reader;

    @ManyToOne
    @JoinColumn
    private Copy copy;


    }
