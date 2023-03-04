package com.kodilla.kodillaLibrary.repository;

import com.kodilla.kodillaLibrary.domain.Copy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CopyRepository extends CrudRepository<Copy, Long> {

    List<Copy> findByStatus(String status);
}
