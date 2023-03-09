package com.kodilla.library.repository;

import com.kodilla.library.domain.Borrow;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BorrowRepository extends CrudRepository<Borrow, Long> {
    List<Borrow> findAll();

    Borrow save(Borrow borrow);

    Optional<Borrow> findById(Long borrowId);

    void deleteById(Long borrowId);
}
