package com.comet.gamequeuemanager.service;

import com.comet.gamequeuemanager.dto.QueueCreationRequestDto;
import com.comet.gamequeuemanager.dto.QueueFile;

import java.io.File;
import java.util.UUID;

public interface QueueService {
    QueueFile createQueueFile(QueueCreationRequestDto queueCreationRequestDto);
    QueueFile readQueueFile(UUID queueId);
    QueueFile updateQueue(QueueFile queue);
    QueueFile clearQueue(UUID queueId);
    QueueFile readQueueFromFile(File queueFIle);
}
