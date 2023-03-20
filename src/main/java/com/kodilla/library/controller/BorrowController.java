package com.kodilla.library.controller;

import com.kodilla.library.domain.Borrow;
import com.kodilla.library.domain.BorrowDto;
import com.kodilla.library.mapper.BorrowMapper;
import com.kodilla.library.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("v1/library/borrow")
public class BorrowController {

    @Autowired
    BorrowService borrowService;
    @Autowired
    private BorrowMapper borrowMapper;

    @GetMapping
    public ResponseEntity<List<BorrowDto>> getBorrows() {
        List<Borrow> borrows = borrowService.getBorrows();
        List<BorrowDto> borrowDtos = borrowMapper.mapToBorrowDtoList(borrows);
        return ResponseEntity.ok(borrowDtos);
    }

    @GetMapping("/{borrowId}")
    public ResponseEntity<BorrowDto> getBorrowById(@PathVariable Long borrowId) {
        Optional<Borrow> borrowOptional = borrowService.getBorrowById(borrowId);
        if (borrowOptional.isPresent()) {
            BorrowDto borrowDto = borrowMapper.mapToBorrowDto(borrowOptional.get());
            return ResponseEntity.ok(borrowDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<BorrowDto> createBorrow(@RequestBody BorrowDto borrowDto) {
        Borrow borrow = borrowMapper.mapToBorrow(borrowDto);
        Borrow createdBorrow = borrowService.saveBorrow(borrow);
        BorrowDto createdBorrowDto = borrowMapper.mapToBorrowDto(createdBorrow);
        return new ResponseEntity<>(createdBorrowDto, HttpStatus.CREATED);
    }
    @DeleteMapping("/{borrowId}")
    public ResponseEntity<String> deleteBorrow(@PathVariable("borrowId") Long borrowId) {
        Optional<Borrow> optionalBorrow = borrowService.getBorrowById(borrowId);
        if (optionalBorrow.isEmpty()) {
            return new ResponseEntity<>("Borrow with id " + borrowId + " not found.", HttpStatus.NOT_FOUND);
        }

        borrowService.deleteBorrowById(borrowId);
        return new ResponseEntity<>("Borrow with id " + borrowId + " deleted.", HttpStatus.OK);
    }

    @PutMapping("/{borrowId}")
    public ResponseEntity<BorrowDto> updateBorrow(
            @PathVariable Long borrowId,
            @RequestBody BorrowDto borrowDto) {
        Optional<Borrow> optionalBorrow = borrowService.getBorrowById(borrowId);

        if (optionalBorrow.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Borrow borrow = borrowMapper.mapToBorrow(borrowDto);
        borrow.setBorrowId(borrowId);

        Borrow updatedBorrow = borrowService.saveBorrow(borrow);
        BorrowDto updatedBorrowDto = borrowMapper.mapToBorrowDto(updatedBorrow);

        return ResponseEntity.ok(updatedBorrowDto);

    }

}

