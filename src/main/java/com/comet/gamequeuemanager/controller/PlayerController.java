package com.comet.gamequeuemanager.controller;

import com.comet.gamequeuemanager.dto.PlayerDto;
import com.comet.gamequeuemanager.dto.QueueFile;
import com.comet.gamequeuemanager.service.PlayerService;
import com.comet.gamequeuemanager.service.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("player/")
public class PlayerController {

    @Autowired
    private PlayerService playerService;
    @Autowired
    private QueueService queueService;

    @PostMapping("{queueId}")
    public ResponseEntity<QueueFile> addPlayerToQueue(@RequestBody PlayerDto playerDto,
                                                      @PathVariable(name = "queueId") UUID queueId) {
        QueueFile queueFile = playerService.addPlayerToQueue(playerDto, queueId);
        queueService.deleteQueueFile(queueFile);
        if (queueFile.getCapacity().equals(queueFile.getPlayersAssigned().size())) {
            return new ResponseEntity<>(queueFile, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(queueFile, HttpStatus.PARTIAL_CONTENT);
        }
    }

    @DeleteMapping("{queueId}")
    public QueueFile getQueue(@RequestBody PlayerDto playerDto,
                              @PathVariable(name = "queueId") UUID queueId) {
        QueueFile queueFile = playerService.deletePlayerFromQueue(playerDto, queueId);
        queueService.deleteQueueFile(queueFile);
        return queueFile;
    }

}
