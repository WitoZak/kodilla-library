package com.kodilla.library.service;

import com.kodilla.library.domain.Borrow;
import com.kodilla.library.repository.BorrowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BorrowService {

    @Autowired
    private final BorrowRepository borrowRepository;

    public List<Borrow> getBorrows() {
        return borrowRepository.findAll();
    }

    public Borrow saveBorrow(final Borrow borrow) {
        return borrowRepository.save(borrow);
    }

    public void deleteBorrowById(Long borrowId) {
        borrowRepository.deleteById(borrowId);
    }

    public Optional<Borrow> getBorrowById(Long borrowId) {
        return borrowRepository.findById(borrowId);
    }

}
