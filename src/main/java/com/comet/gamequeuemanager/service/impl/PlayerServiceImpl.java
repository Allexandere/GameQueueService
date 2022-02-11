package com.comet.gamequeuemanager.service.impl;

import com.comet.gamequeuemanager.dto.PlayerDto;
import com.comet.gamequeuemanager.dto.QueueFile;
import com.comet.gamequeuemanager.repository.QueueRepository;
import com.comet.gamequeuemanager.service.CloudFileService;
import com.comet.gamequeuemanager.service.PlayerService;
import com.comet.gamequeuemanager.service.QueueService;
import com.comet.gamequeuemanager.service.validator.PlayerValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private QueueService queueService;
    @Autowired
    private PlayerValidator playerValidator;
    @Autowired
    private QueueRepository queueRepository;
    @Autowired
    private CloudFileService cLoudFileService;
    @Autowired
    private Web3ServiceImpl web3Service;

    @Override
    public QueueFile addPlayerToQueue(PlayerDto playerDto, UUID queueId) {
        playerValidator.validatePlayerDto(playerDto);
        QueueFile targetQueue = queueService.readQueueFile(queueId);
        targetQueue.getPlayersAssigned().add(playerDto);
        log.info(String.format("Adding player %s to queue %s", playerDto, targetQueue));
        String cid = queueRepository.findById(targetQueue.getId()).get().getCid();
        cLoudFileService.loadFileByUrl(web3Service.getFileUrl(cid), targetQueue.getId());
        if (targetQueue.getPlayersAssigned().size() == targetQueue.getCapacity()) {
            log.info("Queue is full, returning");
            queueService.clearQueue(targetQueue.getId());
        } else {
            queueService.updateQueue(targetQueue);
            return null;
        }
        return targetQueue;
    }

    @Override
    public QueueFile deletePlayerFromQueue(PlayerDto playerDto, UUID queueId) {
        playerValidator.validatePlayerDto(playerDto);
        QueueFile targetQueue = queueService.readQueueFile(queueId);
        playerValidator.checkPlayerPresence(targetQueue, playerDto);
        log.info(String.format("Deleting player %s from queue %s", playerDto, targetQueue));
        targetQueue.getPlayersAssigned().remove(playerDto);
        return queueService.updateQueue(targetQueue);
    }
}
