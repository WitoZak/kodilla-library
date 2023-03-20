package com.kodilla.library.service;

import com.kodilla.library.domain.Reader;
import com.kodilla.library.repository.ReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReaderService {

    public final ReaderRepository readerRepository;

    public List<Reader> getAllReaders() {
        return  readerRepository.findAll();
    }

    public Reader save(Reader reader) {
        return readerRepository.save(reader);
    }

    public Reader getReaderById(final Long readerId) {
        return readerRepository.findById(readerId).orElseThrow();
    }

    public void deleteById(Long readerId) {
        readerRepository.deleteById(readerId);
    }
}
