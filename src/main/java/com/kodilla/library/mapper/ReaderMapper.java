package com.kodilla.library.mapper;

import com.kodilla.library.domain.Reader;
import com.kodilla.library.domain.ReaderDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReaderMapper {

    public Reader mapToReader(final ReaderDto readerDto) {
        Reader reader = new Reader();
        reader.setReaderId(readerDto.getReaderId());
        reader.setFirstName(readerDto.getFirstName());
        reader.setLastName(readerDto.getLastName());
        reader.setAccountCreatingDate(readerDto.getAccountCreatingDate());
        return reader;
    }

    public ReaderDto mapToReaderDto(final Reader reader) {
        return new ReaderDto(
                reader.getReaderId(),
                reader.getFirstName(),
                reader.getLastName(),
                reader.getAccountCreatingDate());
    }

    public List<ReaderDto> mapToReaderDtoList(final List<Reader> readerList) {
        return readerList.stream()
                .map(this::mapToReaderDto)
                .toList();
    }
}
