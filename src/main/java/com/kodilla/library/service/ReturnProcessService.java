package com.kodilla.library.service;

import com.kodilla.library.domain.ReturnProcess;
import com.kodilla.library.repository.ReturnProcessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReturnProcessService {

    private ReturnProcessRepository returnProcessRepository;

    public List<ReturnProcess> getReturnProcess() {
        return (List<ReturnProcess>) returnProcessRepository.findAll();
    }

    public ReturnProcess save(ReturnProcess returnProcess) {
        return returnProcessRepository.save(returnProcess);
    }

    public ReturnProcess getReturnProcessById (final Long id) {
        return returnProcessRepository.findById(id).get();
    }

    public void deleteReturnProcessById (final Long id) {
        returnProcessRepository.deleteById(id);
    }

    public ReturnProcess findReturnProcessByReaderId(Long readerId) {
        return returnProcessRepository.findAll()
                .stream()
                .filter(returnProcess -> readerId.equals(returnProcess.getReaderId()))
                .findFirst().get();
    }
}
