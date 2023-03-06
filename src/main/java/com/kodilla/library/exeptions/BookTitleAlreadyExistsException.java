package com.kodilla.library.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BookTitleAlreadyExistsException extends ResponseStatusException {

    public BookTitleAlreadyExistsException() {
        super(HttpStatus.CONFLICT, "Title already exist");
    }
}