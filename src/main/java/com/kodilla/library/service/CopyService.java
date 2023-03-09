package com.kodilla.library.service;

import com.kodilla.library.domain.Copy;
import com.kodilla.library.repository.CopyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CopyService {

    private final CopyRepository copyRepository;

    private final BookService bookService;

    public List<Copy> getAllCopies() {
        return copyRepository.findAll();
    }

    public Copy updateCopyStatus(Long copyId, String newStatus) {
        Copy copyById = getCopiesById(copyId);
        copyById.setStatus(newStatus);
        saveCopy(copyById);
        return copyById;
    }
    public Copy saveCopy(Copy copy) {
        copy.setBook(bookService.getBookById(copy.getBook().getBookId()));
        return copyRepository.save(copy);
    }
    public Copy getCopiesById(final Long copiesId) {
        return copyRepository.findById(copiesId).get();
    }

    public void deleteCopyById(final Long copiesId) {
        copyRepository.deleteById(copiesId);
    }
}
