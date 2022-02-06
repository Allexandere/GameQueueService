package com.comet.gamequeuemanager.test;

import com.comet.gamequeuemanager.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;

class Web3ServiceTest extends BaseTest {
    @ParameterizedTest
    @ValueSource(strings = {"8ef9592d-8220-4299-9e76-c4c8c875c104",
                            "bf958077-a767-4b80-9110-66491bad7e8a"})
    void testQueueUpload(String queueId) {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        File file = new File(classloader.getResource("test-files/" + queueId + ".json").getFile());
        String cid = web3Service.uploadFile(file);

        Assertions.assertNotNull(cid);
    }
}
