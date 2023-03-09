package com.kodilla.library.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ReaderDto {
    private Long readerId;
    private String firstName;
    private String lastName;
    private Date accountCreatingDate;
}
