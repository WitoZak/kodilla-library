package com.kodilla.library.mapper;

import com.kodilla.library.domain.Borrow;
import com.kodilla.library.domain.BorrowDto;
import com.kodilla.library.service.CopyService;
import com.kodilla.library.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BorrowMapper {

    @Autowired
    private ReaderService readerService;
    @Autowired
    private CopyService copyService;

    public Borrow mapToBorrow(final BorrowDto borrowDto) {
        Borrow borrow = new Borrow();
        borrow.setBorrowId(borrowDto.getBorrowId());
        borrow.setBorrowDate(borrowDto.getBorrowDate());
        borrow.setReturnDate(borrowDto.getReturnDate());
        borrow.setReader(readerService.getReaderById(borrowDto.getReaderId()));
        borrow.setCopy(copyService.getCopiesById(borrowDto.getCopyId()));
        return borrow;
    }

    public BorrowDto mapToBorrowDto(final Borrow borrow) {
        return new BorrowDto(
                borrow.getBorrowId(),
                borrow.getBorrowDate(),
                borrow.getReturnDate(),
                borrow.getReader().getReaderId(),
                borrow.getCopy().getCopiesId());
    }

    public List<BorrowDto> mapToBorrowDtoList(List<Borrow> borrowList) {
        return borrowList.stream()
                .map(this::mapToBorrowDto)
                .toList();
    }
}
