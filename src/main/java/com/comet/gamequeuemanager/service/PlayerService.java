package com.comet.gamequeuemanager.service;

import com.comet.gamequeuemanager.dto.PlayerDto;
import com.comet.gamequeuemanager.dto.QueueFile;

import java.util.UUID;

public interface PlayerService {
    QueueFile addPlayerToQueue(PlayerDto player, UUID queueId);
    QueueFile deletePlayerFromQueue(PlayerDto player, UUID queueId);
}
