package com.comet.gamequeuemanager.test;

import com.comet.gamequeuemanager.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.UUID;

class CloudFileServiceTest extends BaseTest {
    @Test
    void testFileLoading(){
        UUID queueId = UUID.randomUUID();
        File loadedQueue = cloudFileService
                .loadFileByUrl("https://ipfs.io/ipfs/bafkreihy2t62wp5fq2kq65vpgb43nnmpg7h7hf5fcbs55bovxx22jqxcoa", queueId);

        Assertions.assertTrue(loadedQueue.exists());
    }
}
