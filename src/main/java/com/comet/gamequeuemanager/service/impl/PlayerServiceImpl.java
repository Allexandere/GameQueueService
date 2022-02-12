package com.comet.gamequeuemanager.service.impl;

import com.comet.gamequeuemanager.dto.PlayerDto;
import com.comet.gamequeuemanager.dto.QueueFile;
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

    @Override
    public QueueFile addPlayerToQueue(PlayerDto playerDto, UUID queueId) {
        playerValidator.validatePlayerDto(playerDto);

        QueueFile targetQueue = queueService.readQueueFile(queueId);

        targetQueue.getPlayersAssigned().add(playerDto);

        log.info(String.format("Added player %s to queue %s", playerDto, targetQueue));

        if (isQueueFull(targetQueue)) {
            log.info("Queue is full, returning");
            queueService.clearQueue(targetQueue.getId());
        } else {
            queueService.updateQueue(targetQueue);
        }

        return targetQueue;
    }

    private boolean isQueueFull(QueueFile targetQueue) {
        return targetQueue.getPlayersAssigned().size() == targetQueue.getCapacity();
    }

    @Override
    public QueueFile deletePlayerFromQueue(PlayerDto playerDto, UUID queueId) {
        playerValidator.validatePlayerDto(playerDto);

        QueueFile targetQueue = queueService.readQueueFile(queueId);
        playerValidator.checkPlayerPresence(targetQueue, playerDto);

        log.info(String.format("Deleted player %s from queue %s", playerDto, targetQueue));

        targetQueue.getPlayersAssigned().remove(playerDto);

        return queueService.updateQueue(targetQueue);
    }
}
