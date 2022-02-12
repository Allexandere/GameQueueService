package com.comet.gamequeuemanager.test;

import com.comet.gamequeuemanager.BaseTest;
import com.comet.gamequeuemanager.dto.PlayerDto;
import com.comet.gamequeuemanager.dto.QueueCreationRequestDto;
import com.comet.gamequeuemanager.dto.QueueFile;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.UUID;

class PlayerServiceTest extends BaseTest {

    @Test
    @SneakyThrows
    void testPlayerCreation() {
        UUID playerId = UUID.randomUUID();
        PlayerDto player = new PlayerDto(playerId);

        QueueFile queue = queueService.createQueueFile(new QueueCreationRequestDto(3));

        Mockito.when(cloudFileService.loadFileByUrl(Mockito.anyString(), Mockito.any(UUID.class)))
                .thenReturn(createQueueFileForTest(QueueFile.builder()
                        .id(queue.getId())
                        .capacity(3)
                        .playersAssigned(List.of())
                        .build())
                );

        QueueFile queueFileWithPlayer = playerService.addPlayerToQueue(player, queue.getId());

        QueueFile savedPlayerQueue = queueService.readQueueFile(queueFileWithPlayer.getId());

        Assertions.assertEquals(1, savedPlayerQueue.getPlayersAssigned().size());
        Assertions.assertEquals(player.getId(), savedPlayerQueue.getPlayersAssigned().get(0).getId());
    }

}
