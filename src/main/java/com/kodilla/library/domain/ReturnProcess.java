package com.kodilla.library.domain;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table (name = "RETURN_PROCESS")
public class ReturnProcess {

    @Id
    @GeneratedValue
    private Long returnId;

    @JoinColumn
    private Long readerId;

    @JoinColumn
    private Long copyId;


}
