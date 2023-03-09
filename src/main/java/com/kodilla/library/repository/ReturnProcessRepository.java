package com.kodilla.library.repository;

import com.kodilla.library.domain.ReturnProcess;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReturnProcessRepository extends CrudRepository<ReturnProcess, Long> {
    @Override
    List<ReturnProcess> findAll();

    @Override
    ReturnProcess save(ReturnProcess returnProcess);

    @Override
    Optional<ReturnProcess> findById(Long id);

    @Override
    void deleteById(Long id);
}
