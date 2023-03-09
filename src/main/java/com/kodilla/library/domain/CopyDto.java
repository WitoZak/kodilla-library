package com.kodilla.library.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CopyDto {

    private Long copiesId;
    private String status;
    private Long bookId;
}
