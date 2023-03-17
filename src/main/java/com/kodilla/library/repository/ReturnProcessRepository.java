package com.kodilla.library.repository;

import com.kodilla.library.domain.ReturnProcess;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReturnProcessRepository extends CrudRepository<ReturnProcess, Long> {
    List<ReturnProcess> findAll();

}
