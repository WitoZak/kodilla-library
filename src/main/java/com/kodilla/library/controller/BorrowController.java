package com.kodilla.library.controller;

import com.kodilla.library.domain.BorrowDto;
import com.kodilla.library.mapper.BorrowMapper;
import com.kodilla.library.service.BorrowService;
import com.kodilla.library.service.CopyService;
import com.kodilla.library.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("v1/library/borrow")
public class BorrowController {

    @Autowired
    BorrowService borrowService;
    @Autowired
    private CopyService copyService;
    @Autowired
    private ReaderService readerService;
    @Autowired
    private BorrowMapper borrowMapper;

    @GetMapping
    public ResponseEntity<?> getBorrows() {
        List<BorrowDto> borrowDtoList = borrowMapper.mapToBorrowDtoList(borrowService.getBorrows());
        if (!borrowDtoList.isEmpty()) {
            return ResponseEntity.ok().body(borrowDtoList);
        } else {
            return ResponseEntity.badRequest().body("List not found");
            //zwracać obiekt nie string
        }
    }

    @GetMapping("/{borrowId}")
    public ResponseEntity<?> getBorrowById(@PathVariable Long borrowId) {
        if (borrowService.getBorrows()
                .stream()
                .noneMatch(borrow -> borrow.getBorrowId().equals(borrowId))) {
            return ResponseEntity.badRequest().body("Borrow book id: " + borrowId + " doesn't exists.");
        }
        return new ResponseEntity<>(borrowMapper.mapToBorrowDto(borrowService.getBorrowById(borrowId)), HttpStatus.FOUND);
    }

    @PostMapping
    public ResponseEntity<?> createBorrow(@RequestBody BorrowDto borrowDto) throws URISyntaxException {
        if (borrowService.getBorrows()
                .stream()
                .anyMatch(borrow -> borrow.getBorrowId().equals(borrowDto.getBorrowId()))) {
            return ResponseEntity.badRequest().body("Borrow with that id already exists.");
        }

        BorrowDto result = borrowMapper.mapToBorrowDto(borrowService.saveBorrow(borrowMapper.mapToBorrow(borrowDto)));
        copyService.getCopiesById(result.getCopyId()).getBorrows().add(borrowMapper.mapToBorrow(result));
        readerService.getReaderById(result.getReaderId()).getBorrows().add(borrowMapper.mapToBorrow(result));
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(new URI("v1/library/borrow/" + result.getBorrowId()));
        headers.add("KodillaLibraryApplication-alert", "Created entity borrow with id " + result.getBorrowId());
        return new ResponseEntity<>(result, headers, HttpStatus.CREATED);
    }

    @PutMapping("/{borrowId}")
    public ResponseEntity<?> deleteBorrowById(@PathVariable Long borrowId) {
        if (borrowService.getBorrows()
                .stream()
                .noneMatch(borrow -> borrow.getBorrowId().equals(borrowId))) {
            return ResponseEntity.badRequest().body("Borrow with that id doesn't exists.");
        }

        borrowService.deleteBorrowById(borrowId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-KodillaLibraryApplication-alert", "A borrow has been deleted with id " + borrowId);
        return ResponseEntity.noContent().headers(headers).build();
    }

    @PutMapping()
    public ResponseEntity<?> updateBorrow(@RequestBody Long borrowId, BorrowDto borrowDto) {
        boolean exist = borrowService.getBorrows()
                .stream()
                .anyMatch(borrow -> borrow.getBorrowId().equals(borrowDto.getBorrowId()));
        if (borrowDto.getBorrowId() == null) {
            return ResponseEntity.badRequest().body("Wrong borrowId");
        }
        if (!exist) {
            return ResponseEntity.badRequest().body("Borrow with that id:" + borrowDto.getBorrowId() + "doesn't exists.");
        }

        BorrowDto result = borrowMapper.mapToBorrowDto(borrowService.saveBorrow(borrowMapper.mapToBorrow(borrowDto)));
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-KodillaLibraryApplication-Alert", "Entity updated successfully");
        headers.add("X-KodillaLibraryApplication-Log", "Borrow updated with ID: " + borrowDto.getBorrowId());
        return ResponseEntity.ok()
                .headers(headers)
                .body(result);

    }

}

