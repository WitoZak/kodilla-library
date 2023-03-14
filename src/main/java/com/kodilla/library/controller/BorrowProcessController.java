package com.kodilla.library.controller;

import com.kodilla.library.domain.BorrowProcess;
import com.kodilla.library.domain.BorrowProcessDto;
import com.kodilla.library.mapper.BorrowProcessMapper;
import com.kodilla.library.service.BorrowProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("v1/library/borrowProcess")
public class BorrowProcessController {

    private final BorrowProcessService borrowProcessService;
    private final BorrowProcessMapper borrowProcessMapper;

    @Autowired
    public BorrowProcessController(BorrowProcessService borrowProcessService,
                                   BorrowProcessMapper borrowProcessMapper) {
        this.borrowProcessService = borrowProcessService;
        this.borrowProcessMapper = borrowProcessMapper;
    }

    @PostMapping
    public ResponseEntity<BorrowProcessDto> createBorrowProcess(@RequestBody BorrowProcessDto borrowProcessDto) {
        BorrowProcess borrowProcess = borrowProcessMapper.mapToBorrow(borrowProcessDto);
        BorrowProcess createdBorrowProcess = borrowProcessService.saveBorrowProcess(borrowProcess);
        BorrowProcessDto createdBorrowProcessDto = borrowProcessMapper.mapToBorrowProcessDto(createdBorrowProcess);
        return new ResponseEntity<>(createdBorrowProcessDto, HttpStatus.CREATED);
    }

    @GetMapping("/{borrowProcessId}")
    public ResponseEntity<BorrowProcessDto> getBorrowProcess(@PathVariable Long borrowProcessId) {
        Optional<BorrowProcess> borrowProcessOptional = borrowProcessService.getBorrowProcess(borrowProcessId);
        if (borrowProcessOptional.isPresent()) {
            BorrowProcessDto borrowProcessDto = borrowProcessMapper.mapToBorrowProcessDto(borrowProcessOptional.get());
            return ResponseEntity.ok(borrowProcessDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/reader/{readerId}")
    public List<BorrowProcessDto> getBorrowProcessesByReaderId(@PathVariable Long readerId) {
        List<BorrowProcess> borrowProcesses = borrowProcessService.findBorrowProcessByReaderId(readerId);
        return borrowProcessMapper.mapToBorrowDtoList(borrowProcesses);
    }

    @PutMapping("/{borrowProcessId}")
    public ResponseEntity<BorrowProcessDto> updateBorrowProcess(
            @PathVariable Long borrowProcessId,
            @RequestBody BorrowProcessDto borrowProcessDto) {
        Optional<BorrowProcess> optionalBorrowProcess = borrowProcessService.getBorrowProcess(borrowProcessId);

        if (optionalBorrowProcess.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        BorrowProcess borrowProcess = borrowProcessMapper.mapToBorrow(borrowProcessDto);
        borrowProcess.setBorrowProcessId(borrowProcessId);

        BorrowProcess updatedBorrowProcess = borrowProcessService.saveBorrowProcess(borrowProcess);
        BorrowProcessDto updatedBorrowProcessDto = borrowProcessMapper.mapToBorrowProcessDto(updatedBorrowProcess);

        return ResponseEntity.ok(updatedBorrowProcessDto);

    }

    @DeleteMapping("/{borrowProcessId}")
    public ResponseEntity<String> deleteBorrowProcess(@PathVariable("borrowProcessId") Long borrowProcessId) {
        Optional<BorrowProcess> borrowProcess = borrowProcessService.getBorrowProcess(borrowProcessId);
        if (borrowProcess.isEmpty()) {
            return new ResponseEntity<>("Borrow process with id " + borrowProcessId + " not found.", HttpStatus.NOT_FOUND);
        }

        borrowProcessService.deleteBorrowProcessById(borrowProcessId);
        return new ResponseEntity<>("Borrow process with id " + borrowProcessId + " deleted.", HttpStatus.OK);
    }
}
