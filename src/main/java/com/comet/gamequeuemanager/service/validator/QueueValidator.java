package com.comet.gamequeuemanager.service.validator;

import com.comet.gamequeuemanager.dto.QueueCreationRequestDto;
import com.comet.gamequeuemanager.repository.QueueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.util.UUID;

@Component
public class QueueValidator {

    @Autowired
    private QueueRepository queueRepository;

    public void validateQueueDto(QueueCreationRequestDto queueDto) {
        if (queueDto == null || queueDto.getCapacity() == null || queueDto.getCapacity() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public void validateId(UUID id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if(queueRepository.findById(id).isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public void checkQueueForPresentence(String path){
        File queueFile = new File(path);
        if(!queueFile.exists()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
