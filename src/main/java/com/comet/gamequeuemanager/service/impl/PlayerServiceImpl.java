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
        log.info("Saving player, result queue: " + targetQueue);
        if (targetQueue.getPlayersAssigned().size() == targetQueue.getCapacity()) {
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
        targetQueue.getPlayersAssigned().remove(playerDto);
        return queueService.updateQueue(targetQueue);
    }
}
