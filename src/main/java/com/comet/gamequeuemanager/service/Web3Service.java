package com.comet.gamequeuemanager.service;

import java.io.File;

public interface Web3Service {
    String uploadFile(File file);
    String getFileUrl(String cid);
}
