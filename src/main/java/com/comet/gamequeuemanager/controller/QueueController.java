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
        return queueService.createQueueFile(queueCreationRequestDto).getId().toString();
    }

    @GetMapping("{queueId}")
    public QueueFile getQueue(@PathVariable(name = "queueId") UUID queueId){
        return queueService.readQueueFile(queueId);
    }

}
