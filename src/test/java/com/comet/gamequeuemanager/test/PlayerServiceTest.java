package com.comet.gamequeuemanager.test;

import com.comet.gamequeuemanager.BaseTest;
import com.comet.gamequeuemanager.dto.PlayerDto;
import com.comet.gamequeuemanager.dto.QueueCreationRequestDto;
import com.comet.gamequeuemanager.dto.QueueFile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.UUID;

class PlayerServiceTest extends BaseTest {

    @ParameterizedTest
    @ValueSource(ints = {2, 3})
    void testPlayerCreation(Integer capacity) {
        ReflectionTestUtils.setField(queueService, "tmpFolder", "src/test/resources/tmp-files/");
        UUID playerId = UUID.randomUUID();
        PlayerDto player = new PlayerDto(playerId);
        QueueFile queue = queueService.createQueueFile(new QueueCreationRequestDto(capacity));
        playerService.addPlayerToQueue(player, queue.getId());
        QueueFile savedPlayerQueue = queueService.readQueueFile(queue.getId());

        Assertions.assertEquals(1, savedPlayerQueue.getPlayersAssigned().size());
        Assertions.assertEquals(player.getId(), savedPlayerQueue.getPlayersAssigned().get(0).getId());
    }

}
