package com.kodilla.library.service;

import com.kodilla.library.domain.BorrowProcess;
import com.kodilla.library.repository.BorrowProcessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BorrowProcessService {

    private final BorrowProcessRepository borrowProcessRepository;

    public Optional<BorrowProcess> getBorrowProcess(final Long borrowProcessId) {
        return borrowProcessRepository.findById(borrowProcessId);
    }

    public BorrowProcess saveBorrowProcess(final BorrowProcess borrowProcess) {
        return borrowProcessRepository.save(borrowProcess);
    }

    public void deleteBorrowProcessById(final Long borrowProcessId) {
        borrowProcessRepository.deleteById(borrowProcessId);
    }

    public List<BorrowProcess> findBorrowProcessByReaderId(Long readerId) {
        return borrowProcessRepository.findAll().stream()
                .filter(borrowProcess -> readerId.equals(borrowProcess.getReader().getReaderId()))
                .toList();
    }
}

