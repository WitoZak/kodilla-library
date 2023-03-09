package com.kodilla.library.mapper;

import com.kodilla.library.domain.BorrowProcess;
import com.kodilla.library.domain.BorrowProcessDto;
import com.kodilla.library.domain.Reader;
import com.kodilla.library.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BorrowProcessMapper {
    @Autowired
    private ReaderService readerService;

    public BorrowProcess mapToBorrow(final BorrowProcessDto borrowProcessDto) {
        BorrowProcess borrowProcess = new BorrowProcess();
        borrowProcess.setBorrowProcessId(borrowProcessDto.getBorrowProcessId());
        borrowProcess.setBookTitle(borrowProcessDto.getBookTitle());
        borrowProcess.setBorrowDate(borrowProcessDto.getBorrowDate());
        borrowProcess.setReturnDate(borrowProcessDto.getReturnDate());
        Reader reader = new Reader();
        reader.setReaderId(readerService.getReaderById(borrowProcessDto.getReader()).getReaderId());
        reader.setFirstName(readerService.getReaderById(borrowProcessDto.getReader()).getFirstName());
        reader.setLastName(readerService.getReaderById(borrowProcessDto.getReader()).getLastName());
        reader.setAccountCreatingDate(readerService.getReaderById(borrowProcessDto.getReader()).getAccountCreatingDate());
        borrowProcess.setReader(reader);
        return borrowProcess;
    }

    public BorrowProcessDto mapToBorrowProcessDto(final BorrowProcess borrowProcess) {
        return new BorrowProcessDto(
                borrowProcess.getBorrowProcessId(),
                borrowProcess.getReader().getReaderId(),
                borrowProcess.getBookTitle(),
                borrowProcess.getBorrowDate(),
                borrowProcess.getReturnDate());

    }

    public List<BorrowProcessDto> mapToBorrowDtoList(final List<BorrowProcess> list) {
        return list.stream()
                .map(borrow -> new BorrowProcessDto(
                        borrow.getBorrowProcessId(),
                        borrow.getReader().getReaderId(),
                        borrow.getBookTitle(),
                        borrow.getBorrowDate(),
                        borrow.getReturnDate()
                ))
                .toList();
    }

}
