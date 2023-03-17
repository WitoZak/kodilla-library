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

    @ManyToOne
    @JoinColumn(name = "READER_ID")
    private Reader reader;

    @JoinColumn
    private Long copyId;
}
