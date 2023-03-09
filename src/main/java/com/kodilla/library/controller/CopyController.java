package com.kodilla.library.controller;

import com.kodilla.library.domain.CopyDto;
import com.kodilla.library.exeptions.NotFoundException;
import com.kodilla.library.mapper.CopyMapper;
import com.kodilla.library.service.BookService;
import com.kodilla.library.service.CopyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/library/copy")
public class CopyController {

    @Autowired
    CopyMapper copyMapper;
    @Autowired
    CopyService copyService;
    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<?> getCopies() throws NotFoundException {
        List<CopyDto> copyDtoList = copyMapper.mapToCopyDtoList(copyService.getAllCopies());
        if (!copyDtoList.isEmpty()) {
            return ResponseEntity.ok().body(copyDtoList);
        } else {
            throw new NotFoundException("List not found");
        }
    }

    @GetMapping("/{copyId}")
    public ResponseEntity<?> getCopyById(@PathVariable Long copyId) {
        if (copyService.getAllCopies()
                .stream()
                .noneMatch(copy -> copy.getCopiesId().equals(copyId))) {
            return ResponseEntity.badRequest().body("Copy id" + copyId + " doesn't exists.");
        }
        return new ResponseEntity<>(copyMapper.mapToCopyDto(copyService.getCopiesById(copyId)), HttpStatus.FOUND);
    }

    @PostMapping
    public ResponseEntity<?> createCopy(@RequestBody CopyDto copyDto) throws URISyntaxException {
        if (copyService.getAllCopies()
                .stream()
                .anyMatch(copy -> copy.getCopiesId().equals(copyDto.getCopiesId()))) {
            return ResponseEntity.badRequest().body("Copy id " + copyDto.getCopiesId() + " already exists.");
        }

        CopyDto result = copyMapper.mapToCopyDto(copyService.saveCopy(copyMapper.mapToCopy(copyDto)));
        bookService.getBookById(result.getBookId()).getCopyList().add(copyMapper.mapToCopy(result));
        URI location = new URI("/copy" + result.getCopiesId());
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);
        headers.add("KodillaLibraryApplication-alert", "copy.created");
        headers.add("KodillaLibraryApplication-params", result.getCopiesId().toString());

        return ResponseEntity.created(location).headers(headers).body(result);
    }

    @PutMapping
    public ResponseEntity<?> updateCopy(@RequestBody CopyDto copyDto) {
        boolean exists = copyService.getAllCopies()
                .stream()
                .anyMatch(copy -> copy.getCopiesId().equals(copyDto.getCopiesId()));
        if (copyDto.getCopiesId() == null) {
            return ResponseEntity.badRequest().body("CopyId is wrong.");
        }
        if (!exists) {
            return ResponseEntity.badRequest().body("Copy with that Id: " + copyDto.getCopiesId() + "doesn't exists");

        }

        CopyDto result = copyMapper.mapToCopyDto(copyService.saveCopy(copyMapper.mapToCopy(copyDto)));
        HttpHeaders headers = new HttpHeaders();
        headers.set("KodillaLibraryApplicationp-Update", "copy");
        headers.set("KodillaLibraryApplication-Id", copyDto.getCopiesId().toString());
        return ResponseEntity.ok()
                .headers(headers)
                .body(result);
    }

    @DeleteMapping("/{copyId}")
    public ResponseEntity<?> deleteCopyById(@PathVariable Long copyId) {
        if (copyService.getAllCopies()
                .stream()
                .noneMatch(copy -> copy.getCopiesId().equals(copyId))) {
            return ResponseEntity.badRequest().body("Copy with that Id: " + copyId + "doesn't exists");
        }

        copyService.deleteCopyById(copyId);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.add(HttpHeaders.LOCATION, ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString());

        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
    }
}
