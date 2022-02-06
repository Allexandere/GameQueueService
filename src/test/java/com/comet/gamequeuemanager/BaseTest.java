package com.comet.gamequeuemanager;

import com.comet.gamequeuemanager.service.CloudFileService;
import com.comet.gamequeuemanager.service.PlayerService;
import com.comet.gamequeuemanager.service.QueueService;
import com.comet.gamequeuemanager.service.Web3Service;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.File;

@SpringBootTest
@ComponentScan
@ContextConfiguration(classes = PostgreTestConfig.class)
@Import(PostgreTestConfig.class)
public class BaseTest {

    @Autowired
    protected QueueService queueService;
    @Autowired
    protected PlayerService playerService;
    @Autowired
    protected Web3Service web3Service;
    @Autowired
    protected CloudFileService cloudFileService;

    @BeforeEach
    void setTmpFolder() {
        ReflectionTestUtils.setField(queueService, "tmpFolder", "src/test/resources/tmp-files/");
        ReflectionTestUtils.setField(cloudFileService, "tmpFolder", "src/test/resources/tmp-files/");
    }

    @AfterEach
    void clearTmpFolderAndUpdatePath() {
        File tmpFolder = new File("src/test/resources/tmp-files/");
        for (File file : tmpFolder.listFiles()) {
            file.delete();
        }
    }
}
