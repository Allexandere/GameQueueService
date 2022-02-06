package com.comet.gamequeuemanager.test;

import com.comet.gamequeuemanager.BaseTest;
import com.comet.gamequeuemanager.dto.PlayerDto;
import com.comet.gamequeuemanager.dto.QueueCreationRequestDto;
import com.comet.gamequeuemanager.dto.QueueFile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.File;
import java.util.UUID;
import java.util.stream.Stream;

class QueueServiceTest extends BaseTest {

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 10, 20})
    void testQueueCreation(Integer capacity) {
        QueueFile createdQueue = queueService.createQueueFile(new QueueCreationRequestDto(capacity));
        QueueFile queueFileObject = queueService.readQueueFile(createdQueue.getId());

        Assertions.assertEquals(capacity, queueFileObject.getCapacity());
    }

    @ParameterizedTest
    @MethodSource("objectFilesProvider")
    void testQueueReading(UUID fileId, QueueFile originalQueueFile) {
        QueueFile loadedQueueFile = queueService.readQueueFromFile(new File("src/test/resources/test-files/" + fileId + ".json"));

        Assertions.assertEquals(originalQueueFile, loadedQueueFile);
    }

    private static Stream<Arguments> objectFilesProvider() {
        return Stream.of(
                Arguments.of(UUID.fromString("8ef9592d-8220-4299-9e76-c4c8c875c104"),
                        new QueueFile(UUID.fromString("8ef9592d-8220-4299-9e76-c4c8c875c104"), 3)),
                Arguments.of(UUID.fromString("bf958077-a767-4b80-9110-66491bad7e8a"),
                        new QueueFile(UUID.fromString("bf958077-a767-4b80-9110-66491bad7e8a"), 1))
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 10, 20})
    void testQueueUpdate(Integer capacity) {
        QueueFile createdQueue = queueService.createQueueFile(new QueueCreationRequestDto(capacity));

        createdQueue.setCapacity(capacity + 5);
        createdQueue.getPlayersAssigned().add(new PlayerDto(UUID.randomUUID()));
        createdQueue.getPlayersAssigned().add(new PlayerDto(UUID.randomUUID()));
        createdQueue.getPlayersAssigned().add(new PlayerDto(UUID.randomUUID()));

        QueueFile updatedQueue = queueService.updateQueue(createdQueue);

        Assertions.assertEquals(3, updatedQueue.getPlayersAssigned().size());
        Assertions.assertEquals(capacity + 5, updatedQueue.getCapacity());
    }

    @Test
    void testQueueCleaning() {
        QueueFile createdQueue = queueService.createQueueFile(new QueueCreationRequestDto(1));

        createdQueue.getPlayersAssigned().add(new PlayerDto(UUID.randomUUID()));
        createdQueue.getPlayersAssigned().add(new PlayerDto(UUID.randomUUID()));
        createdQueue.getPlayersAssigned().add(new PlayerDto(UUID.randomUUID()));

        QueueFile updatedQueue = queueService.updateQueue(createdQueue);
        QueueFile cleanedQueue = queueService.clearQueue(updatedQueue.getId());

        Assertions.assertEquals(3, updatedQueue.getPlayersAssigned().size());
        Assertions.assertEquals(0, cleanedQueue.getPlayersAssigned().size());
        Assertions.assertEquals(updatedQueue.getCapacity(), cleanedQueue.getCapacity());
        Assertions.assertEquals(updatedQueue.getId(), cleanedQueue.getId());
    }
}
