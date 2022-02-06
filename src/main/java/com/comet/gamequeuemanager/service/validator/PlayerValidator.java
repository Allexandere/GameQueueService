package com.comet.gamequeuemanager.service.validator;

import com.comet.gamequeuemanager.dto.PlayerDto;
import com.comet.gamequeuemanager.dto.QueueCreationRequestDto;
import com.comet.gamequeuemanager.dto.QueueFile;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Component
public class PlayerValidator {
    public void validatePlayerDto(PlayerDto playerDto) {
        if (playerDto == null || playerDto.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public void checkPlayerPresence(QueueFile queueFile, PlayerDto player) {
        if (queueFile.getPlayersAssigned().stream().noneMatch(playerDto -> playerDto.getId().equals(player.getId()))) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
