package com.kodilla.library.controller;

import com.kodilla.library.domain.Reader;
import com.kodilla.library.domain.ReaderDto;
import com.kodilla.library.mapper.ReaderMapper;
import com.kodilla.library.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/library/reader")
public class ReaderController {

    private final ReaderService readerService;
    private final ReaderMapper readerMapper;

    @Autowired
    public ReaderController(ReaderService readerService, ReaderMapper readerMapper) {
        this.readerService = readerService;
        this.readerMapper = readerMapper;
    }

    @PostMapping
    public ResponseEntity<ReaderDto> createReader(@RequestBody ReaderDto readerDto) {
        Reader reader = readerMapper.mapToReader(readerDto);
        Reader saveReader = readerService.save(reader);
        ReaderDto saveReaderDto = readerMapper.mapToReaderDto(saveReader);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveReaderDto);
    }

    @GetMapping
    public List<ReaderDto> getReaders() {
        List<Reader> readerList = readerService.getAllReaders();
        return readerMapper.mapToReaderDtoList(readerList);
    }

    @GetMapping("/{readerId}")
    public ResponseEntity<ReaderDto> getReaderById(@PathVariable Long readerId) {
        Reader reader = readerService.getReaderById(readerId);
        if (reader == null) {
            return ResponseEntity.notFound().build();
        }
        ReaderDto readerDto = readerMapper.mapToReaderDto(reader);
        return ResponseEntity.ok(readerDto);
    }

    @PutMapping("/{readerId}")
    public ResponseEntity<ReaderDto> updateReader(@PathVariable Long readerId, @RequestBody ReaderDto readerDto) {
        Reader optionalReader = readerService.getReaderById(readerId);
        if (optionalReader != null) {
            optionalReader.setFirstName(readerDto.getFirstName());
            optionalReader.setLastName(readerDto.getLastName());
            optionalReader.setAccountCreatingDate(readerDto.getAccountCreatingDate());
            Reader updatedReader = readerService.save(optionalReader);
            ReaderDto updatedReaderDto = readerMapper.mapToReaderDto(updatedReader);
            return ResponseEntity.ok(updatedReaderDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
