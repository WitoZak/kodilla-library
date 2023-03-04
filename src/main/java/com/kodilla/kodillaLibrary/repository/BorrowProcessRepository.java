package com.kodilla.kodillaLibrary.repository;

import com.kodilla.kodillaLibrary.domain.BorrowProcess;
import com.kodilla.kodillaLibrary.domain.Reader;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowProcessRepository extends CrudRepository <BorrowProcess, Long> {

    List<BorrowProcess>findBorrowProcessByReader (Reader reader);
}
