package com.kodilla.library.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class BorrowProcessDto {

    private Long borrowProcessId;
    private Long reader;
    private String bookTitle;
    private LocalDate borrowDate;
    private LocalDate returnDate;
}
