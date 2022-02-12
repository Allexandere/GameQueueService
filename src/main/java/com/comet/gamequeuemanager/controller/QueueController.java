package com.comet.gamequeuemanager.controller;

import com.comet.gamequeuemanager.dto.QueueCreationRequestDto;
import com.comet.gamequeuemanager.dto.QueueFile;
import com.comet.gamequeuemanager.service.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("queue/")
public class QueueController {

    @Autowired
    private QueueService queueService;

    @PostMapping
    public String createQueue(@RequestBody QueueCreationRequestDto queueCreationRequestDto){
        QueueFile queueFile = queueService.createQueueFile(queueCreationRequestDto);
        queueService.deleteQueueFile(queueFile);
        return queueFile.getId().toString();
    }

    @GetMapping("{queueId}")
    public QueueFile getQueue(@PathVariable(name = "queueId") UUID queueId) {
        QueueFile queueFile = queueService.readQueueFile(queueId);
        queueService.deleteQueueFile(queueFile);
        return queueFile;
    }

}
