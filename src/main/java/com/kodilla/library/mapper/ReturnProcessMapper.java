package com.kodilla.library.mapper;

import com.kodilla.library.domain.Reader;
import com.kodilla.library.domain.ReturnProcess;
import com.kodilla.library.domain.ReturnProcessDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReturnProcessMapper {

    public ReturnProcess mapToReturnProcess(ReturnProcessDto returnProcessDto) {
        ReturnProcess returnProcess = new ReturnProcess();
        returnProcess.setReturnId(returnProcessDto.getReturnProcessId());

        Reader reader = new Reader();
        reader.setReaderId(returnProcessDto.getReaderId());
        returnProcess.setReader(reader);

        returnProcess.setCopyId(returnProcessDto.getCopyId());

        return returnProcess;
    }

    public ReturnProcessDto mapToReturnProcessDto(ReturnProcess returnProcess) {
        ReturnProcessDto returnProcessDto = new ReturnProcessDto();
        returnProcessDto.setReturnProcessId(returnProcess.getReturnId());
        returnProcessDto.setReaderId(returnProcess.getReader().getReaderId());
        returnProcessDto.setCopyId(returnProcess.getCopyId());

        return returnProcessDto;
    }

    public List<ReturnProcessDto> mapToReturnProcessDtoList(final List<ReturnProcess> returnProcessList) {
        return returnProcessList
                .stream()
                .map(this::mapToReturnProcessDto)
                .toList();
    }
}
