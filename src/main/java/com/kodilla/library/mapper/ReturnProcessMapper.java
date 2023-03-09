package com.kodilla.library.mapper;

import com.kodilla.library.domain.ReturnProcess;
import com.kodilla.library.domain.ReturnProcessDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReturnProcessMapper {

    public ReturnProcess mapToReturnProcess(final ReturnProcessDto returnProcessDto) {
        return new ReturnProcess(returnProcessDto.getReturnProcessId(), returnProcessDto.getReaderId(), returnProcessDto.getCopyId());
    }

    public ReturnProcessDto mapToReturnProcessDto(final ReturnProcess returnProcess) {
        return new ReturnProcessDto(returnProcess.getReturnId(), returnProcess.getReaderId(), returnProcess.getCopyId());
    }

    public List<ReturnProcessDto> mapToReturnProcessDtoList(final List<ReturnProcess> returnProcessList) {
        return returnProcessList
                .stream()
                .map(this::mapToReturnProcessDto)
                .toList();
    }
}
