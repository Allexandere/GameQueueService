package com.comet.gamequeuemanager.service.impl;

import com.comet.gamequeuemanager.service.CloudFileService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.UUID;

@Service
@Slf4j
public class CloudFileServiceImpl implements CloudFileService {

    @Value("${files-path}")
    private String tmpFolder;

    @Override
    public File loadFileByUrl(String url, UUID queueId) {
        try {
            File queueFile = new File(tmpFolder + queueId.toString() + ".json");
            log.info("Downloaded a file in " + queueFile.getAbsolutePath());
            FileUtils.copyURLToFile(new URL(url), queueFile);
            return queueFile;
        } catch (IOException e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
