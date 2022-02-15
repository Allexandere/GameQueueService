package com.comet.gamequeuemanager;

import com.comet.gamequeuemanager.adapters.Web3StorageAdapter;
import com.comet.gamequeuemanager.dto.QueueFile;
import com.comet.gamequeuemanager.repository.QueueRepository;
import com.comet.gamequeuemanager.service.CloudFileService;
import com.comet.gamequeuemanager.service.PlayerService;
import com.comet.gamequeuemanager.service.QueueService;
import com.comet.gamequeuemanager.service.Web3Service;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils;

import java.io.File;

@SpringBootTest
@Import(PostgreTestConfig.class)
@ContextConfiguration(classes = PostgreTestConfig.class)
public class BaseTest {

    @MockBean
    protected Web3Service web3Service;
    @MockBean
    protected Web3StorageAdapter web3StorageAdapter;
    @MockBean
    protected CloudFileService cloudFileService;
    @Autowired
    protected QueueService queueService;
    @Autowired
    protected PlayerService playerService;
    @Autowired
    protected QueueRepository queueRepository;

    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    @SneakyThrows
    void setTmpFolder() {
        Mockito.when(web3StorageAdapter.uploadFile(Mockito.any(File.class))).thenReturn(RandomStringUtils.randomAlphanumeric(30));
        Mockito.when(web3Service.getFileUrl(Mockito.anyString())).thenReturn(RandomStringUtils.randomAlphanumeric(30));
        ReflectionTestUtils.setField(queueService, "tmpFolder", "./src/test/resources/tmp-files/");
    }

    @AfterEach
    void clearTmpFolderAndUpdatePath() {
        File tmpFolder = new File("./src/test/resources/tmp-files/");
        for (File file : tmpFolder.listFiles()) {
            file.delete();
        }
    }

    @SneakyThrows
    protected File createQueueFileForTest(QueueFile queueObject) {
        File queueFile = new File("./src/test/resources/tmp-files/" + queueObject.getId() + ".json");
        mapper.writeValue(queueFile, queueObject);
        return queueFile;
    }
}
