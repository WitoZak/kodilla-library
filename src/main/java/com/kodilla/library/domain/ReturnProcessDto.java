package com.kodilla.library.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReturnProcessDto {

    private Long returnProcessId;
    private Long readerId;
    private Long copyId;

}
