package com.kodilla.library.service;

import com.kodilla.library.domain.Reader;
import com.kodilla.library.repository.ReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReaderService {

    public final ReaderRepository readerRepository;

    public List<Reader> getAllReaders() {
        return (List<Reader>) readerRepository.findAll();
    }

    public Reader save(Reader reader) {
        return readerRepository.save(reader);
    }

    public Reader getReaderById(final Long id) {
        return readerRepository.findById(id).get();
    }

    public void deleteById(Long id) {
        readerRepository.deleteById(id);
    }
}
