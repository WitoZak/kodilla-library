package com.kodilla.library.service;

import com.kodilla.library.domain.ReturnProcess;
import com.kodilla.library.repository.ReturnProcessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReturnProcessService {

    private final ReturnProcessRepository returnProcessRepository;

    public List<ReturnProcess> getReturnProcess() {
        return returnProcessRepository.findAll();
    }

    public ReturnProcess saveReturnProcess(final ReturnProcess returnProcess) {
        return returnProcessRepository.save(returnProcess);
    }

    public Optional<ReturnProcess> getReturnProcessById(final Long returnProcessId) {
        return returnProcessRepository.findById(returnProcessId);
    }

    public void deleteReturnProcessById (final Long returnProcessId) {
        returnProcessRepository.deleteById(returnProcessId);
    }

    public List<ReturnProcess> findReturnProcessByReaderId(Long readerId) {
        return returnProcessRepository.findAll()
                .stream()
                .filter(returnProcess -> readerId.equals(returnProcess.getReader().getReaderId()))
                .toList();
    }
}
