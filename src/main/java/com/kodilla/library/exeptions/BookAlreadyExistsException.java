package com.kodilla.library.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BookAlreadyExistsException extends ResponseStatusException {

    public BookAlreadyExistsException(){
        super(HttpStatus.CONFLICT, "Book already exists");
    }
}
