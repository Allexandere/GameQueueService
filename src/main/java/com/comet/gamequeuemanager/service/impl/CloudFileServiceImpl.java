package com.comet.gamequeuemanager.service.impl;

import com.comet.gamequeuemanager.service.CloudFileService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.UUID;

@Service
public class CloudFileServiceImpl implements CloudFileService {

    @Value("${files-path}")
    private String tmpFolder;

    @Override
    public File loadFileByUrl(String url, UUID queueId) {
        try {
            File queueFile = new File(tmpFolder + queueId.toString() + ".json");
            FileUtils.copyURLToFile(new URL(url), queueFile);
            return queueFile;
        } catch (IOException e) {
            return null;
        }
    }
}
