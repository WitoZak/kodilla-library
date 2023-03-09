package com.kodilla.library.repository;

import com.kodilla.library.domain.Borrow;
import lombok.Data;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BorrowRepository extends CrudRepository<Borrow, Long> {
    @Override
    List<Borrow> findAll();

    @Override
    Borrow save(Borrow borrow);

    @Override
    Optional<Borrow> findById(Long id);

    @Override
    void deleteById(Long id);
}
