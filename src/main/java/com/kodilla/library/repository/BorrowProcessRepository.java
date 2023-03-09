package com.kodilla.library.repository;

import com.kodilla.library.domain.BorrowProcess;
import com.kodilla.library.domain.Reader;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BorrowProcessRepository extends CrudRepository<BorrowProcess, Long> {
    @Override
    List<BorrowProcess> findAll();

    @Override
    BorrowProcess save(BorrowProcess borrowProcess);

    @Override
    Optional<BorrowProcess> findById(Long id);

    @Override
    void deleteById(Long id);
}
