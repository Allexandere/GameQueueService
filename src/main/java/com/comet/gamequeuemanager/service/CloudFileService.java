package com.comet.gamequeuemanager.service;

import java.io.File;
import java.util.UUID;

public interface CloudFileService {
    File loadFileByUrl(String url, UUID queueId);
}
