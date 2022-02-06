package com.comet.gamequeuemanager.test;

import com.comet.gamequeuemanager.BaseTest;
import com.comet.gamequeuemanager.dto.PlayerDto;
import com.comet.gamequeuemanager.dto.QueueCreationRequestDto;
import com.comet.gamequeuemanager.dto.QueueFile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.UUID;
import java.util.stream.Stream;

class PlayerServiceTest extends BaseTest {

    @ParameterizedTest
    @ValueSource(ints = {2, 3})
    void testPlayerCreation(Integer capacity) {
        ReflectionTestUtils.setField(queueService, "tmpFolder", "src/test/resources/tmp-files/");
        UUID playerId = UUID.randomUUID();
        PlayerDto player = new PlayerDto(playerId);
        QueueFile queue = queueService.createQueueFile(new QueueCreationRequestDto(capacity));
        QueueFile savedPlayerQueue = playerService.addPlayerToQueue(player, queue.getId());
        QueueFile savedQueue = queueService.readQueueFile(queue.getId());

        Assertions.assertEquals(1, savedQueue.getPlayersAssigned().size());
        Assertions.assertEquals(player.getId(), savedPlayerQueue.getPlayersAssigned().get(0).getId());
    }

}
