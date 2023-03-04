package com.kodilla.kodillaLibrary.repository;

import com.kodilla.kodillaLibrary.domain.Reader;
import lombok.Data;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReaderRepository extends CrudRepository<Reader, Long> {

    List<Reader> findByFirstName(String firstName);

    List<Reader> findByAccountCreatingDate(Data date);
}
