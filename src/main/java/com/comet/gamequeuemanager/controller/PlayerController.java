package com.comet.gamequeuemanager.controller;

import com.comet.gamequeuemanager.dto.PlayerDto;
import com.comet.gamequeuemanager.dto.QueueFile;
import com.comet.gamequeuemanager.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("player/")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @PostMapping("{queueId}")
    public QueueFile addPlayerToQueue(@RequestBody PlayerDto playerDto,
                                      @PathVariable(name = "queueId") UUID queueId){
        return playerService.addPlayerToQueue(playerDto, queueId);
    }

    @DeleteMapping("{queueId}")
    public QueueFile getQueue(@RequestBody PlayerDto playerDto,
                              @PathVariable(name = "queueId") UUID queueId){
        return playerService.deletePlayerFromQueue(playerDto,queueId);
    }

}
