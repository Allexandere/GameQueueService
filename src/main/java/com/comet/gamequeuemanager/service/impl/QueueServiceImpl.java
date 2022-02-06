package com.comet.gamequeuemanager.service.impl;

import com.comet.gamequeuemanager.adapters.Web3StorageAdapter;
import com.comet.gamequeuemanager.dto.QueueCreationRequestDto;
import com.comet.gamequeuemanager.dto.QueueFile;
import com.comet.gamequeuemanager.entity.QueueObject;
import com.comet.gamequeuemanager.repository.QueueRepository;
import com.comet.gamequeuemanager.service.CloudFileService;
import com.comet.gamequeuemanager.service.QueueService;
import com.comet.gamequeuemanager.service.validator.QueueValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
public class QueueServiceImpl implements QueueService {

    @Value("${files-path}")
    private String tmpFolder;
    private final static String fileEnding = ".json";
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private QueueValidator queueValidator;
    @Autowired
    private Web3StorageAdapter web3StorageAdapter;
    @Autowired
    private QueueRepository queueRepository;
    @Autowired
    private CloudFileService cLoudFileService;
    @Autowired
    private Web3ServiceImpl web3Service;

    @Override
    public QueueFile createQueueFile(QueueCreationRequestDto queueCreationRequestDto) {
        queueValidator.validateQueueDto(queueCreationRequestDto);
        UUID id = UUID.randomUUID();
        QueueFile queueFileObject = new QueueFile(id, queueCreationRequestDto.getCapacity());
        try {
            File queueFile = new File(tmpFolder + id.toString() + fileEnding);
            mapper.writeValue(queueFile, queueFileObject);
            log.info("Created file with contents: " + queueFile.toString());
            String cid = web3StorageAdapter.uploadFile(queueFile);
            QueueObject queueEntity = new QueueObject(queueFileObject.getId(), cid);
            queueRepository.save(queueEntity);
            queueFile.delete();
            return queueFileObject;
        } catch (IOException | UnirestException e) {
            log.info(e.getMessage());
            return null;
        }
    }

    @Override
    public QueueFile readQueueFile(UUID queueId) {
        queueValidator.validateId(queueId);
        String cid = queueRepository.findById(queueId).get().getCid();
        try {
            File queueFile = cLoudFileService.loadFileByUrl(web3Service.getFileUrl(cid), queueId);
            QueueFile queueObject = mapper.readValue(queueFile, QueueFile.class);
            log.info("Read queue file with contents: " + queueFile.toString());
            queueFile.delete();
            return queueObject;
        } catch (IOException e) {
            log.info(e.getMessage());
            return null;
        }
    }

    @Override
    public QueueFile updateQueue(QueueFile queue) {
        String cid = queueRepository.findById(queue.getId()).get().getCid();
        try {
            cLoudFileService.loadFileByUrl(web3Service.getFileUrl(cid), queue.getId());
            File queueFile = new File(tmpFolder + queue.getId().toString() + fileEnding);
            mapper.writeValue(queueFile, queue);
            String newCid = web3StorageAdapter.uploadFile(queueFile);
            QueueObject updatedQueueObject = new QueueObject(queue.getId(), newCid);
            queueRepository.save(updatedQueueObject);
            log.info("Created file with contents: " + queue.toString());
            queueFile.delete();
            return queue;
        } catch (IOException | UnirestException e) {
            log.info(e.getMessage());
            return null;
        }
    }

    @Override
    public QueueFile clearQueue(UUID queueId) {
        queueValidator.validateId(queueId);
        String cid = queueRepository.findById(queueId).get().getCid();
        File queueFile = cLoudFileService.loadFileByUrl(web3Service.getFileUrl(cid), queueId);
        QueueFile queueFileObject = this.readQueueFromFile(queueFile);
        queueFileObject.getPlayersAssigned().clear();
        this.updateQueue(queueFileObject);
        log.info("Cleared queue file with contents: " + queueFile.toString());
        return queueFileObject;
    }

    @Override
    public QueueFile readQueueFromFile(File queueFIle) {
        try {
            QueueFile queueFile = mapper.readValue(queueFIle, QueueFile.class);
            log.info("Read queue from file with contents: " + queueFile.toString());
            return queueFile;
        } catch (IOException e) {
            log.info(e.getMessage());
            return null;
        }
    }
}
