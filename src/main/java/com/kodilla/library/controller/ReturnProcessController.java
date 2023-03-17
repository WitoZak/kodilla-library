package com.kodilla.library.controller;

import com.kodilla.library.domain.ReturnProcess;
import com.kodilla.library.domain.ReturnProcessDto;
import com.kodilla.library.mapper.ReturnProcessMapper;
import com.kodilla.library.service.ReturnProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("v1/library/returnProcess")
public class ReturnProcessController {


    private final ReturnProcessService returnProcessService;
    private final ReturnProcessMapper returnProcessMapper;

    @Autowired
    public ReturnProcessController(ReturnProcessService returnProcessService,
                                   ReturnProcessMapper returnProcessMapper) {
        this.returnProcessService = returnProcessService;
        this.returnProcessMapper = returnProcessMapper;
    }

    @GetMapping
    public ResponseEntity<List<ReturnProcessDto>> getAllReturnProcess() {
        List<ReturnProcess> returnProcesses = returnProcessService.getReturnProcess();
        List<ReturnProcessDto> returnProcessDtos = returnProcessMapper.mapToReturnProcessDtoList(returnProcesses);
        return ResponseEntity.ok(returnProcessDtos);
    }

    @GetMapping("/{returnProcessId}")
    public ResponseEntity<ReturnProcessDto> getReturnProcessById(@PathVariable Long returnProcessId) {
        Optional<ReturnProcess> returnProcessOptional = returnProcessService.getReturnProcessById(returnProcessId);
        if (returnProcessOptional.isPresent()) {
            ReturnProcessDto returnProcessDto = returnProcessMapper.mapToReturnProcessDto(returnProcessOptional.get());
            return ResponseEntity.ok(returnProcessDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<ReturnProcessDto> addReturnProcess(@RequestBody ReturnProcessDto returnProcessDto) {
        ReturnProcess returnProcess = returnProcessMapper.mapToReturnProcess(returnProcessDto);
        returnProcess = returnProcessService.saveReturnProcess(returnProcess);
        ReturnProcessDto savedReturnProcessDto = returnProcessMapper.mapToReturnProcessDto(returnProcess);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedReturnProcessDto);

    }

    @PutMapping("/{returnProcessId}")
    public ResponseEntity<ReturnProcessDto> updateReturnProcess(@PathVariable Long returnProcessId,
                                                                @RequestBody ReturnProcessDto returnProcessDto) {
        ReturnProcess returnProcess = returnProcessMapper.mapToReturnProcess(returnProcessDto);
        returnProcess.setReturnId(returnProcessId);
        returnProcess = returnProcessService.saveReturnProcess(returnProcess);
        ReturnProcessDto updatedReturnProcessDto = returnProcessMapper.mapToReturnProcessDto(returnProcess);
        return ResponseEntity.ok(updatedReturnProcessDto);
    }

    @DeleteMapping("/{returnProcessId}")
    public ResponseEntity<String> deleteReturnProcess(@PathVariable("returnProcessId") Long returnProcessId) {
        Optional<ReturnProcess> returnProcess = returnProcessService.getReturnProcessById(returnProcessId);
        if (returnProcess.isEmpty()) {
            return new ResponseEntity<>("Borrow process with id " + returnProcessId + " not found.", HttpStatus.NOT_FOUND);
        }

        returnProcessService.deleteReturnProcessById(returnProcessId);
        return new ResponseEntity<>("Borrow process with id " + returnProcessId + " deleted.", HttpStatus.OK);
    }
}
