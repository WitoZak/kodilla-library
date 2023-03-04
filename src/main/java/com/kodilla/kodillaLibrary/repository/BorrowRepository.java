package com.kodilla.kodillaLibrary.repository;

import com.kodilla.kodillaLibrary.domain.Borrow;
import lombok.Data;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowRepository extends CrudRepository<Borrow, Long> {

    List<Borrow> findBorrowByBorrowDate(Data data);

    List<Borrow> findByReaderLastName(String lastName);

    List<Borrow> findByCopyStatus(String status);
}
