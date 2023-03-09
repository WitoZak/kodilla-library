package com.kodilla.library.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class BorrowDto {

    private Long borrowId;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private Long readerId;
    private Long copyId;
}
