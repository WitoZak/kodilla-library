package com.kodilla.kodillaLibrary.repository;

import com.kodilla.kodillaLibrary.domain.ReturnProcess;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReturnProcessRepository extends CrudRepository<ReturnProcess, Long> {


}
