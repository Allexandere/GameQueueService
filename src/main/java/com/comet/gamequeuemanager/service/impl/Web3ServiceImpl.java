package com.comet.gamequeuemanager.service.impl;

import com.comet.gamequeuemanager.adapters.Web3StorageAdapter;
import com.comet.gamequeuemanager.service.Web3Service;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@Slf4j
public class Web3ServiceImpl implements Web3Service {

    private static final String IPFS_URL = "https://ipfs.io/ipfs/";

    @Autowired
    private Web3StorageAdapter web3StorageAdapter;

    @Override
    public String uploadFile(File file) {
        try {
            return web3StorageAdapter.uploadFile(file);
        } catch (UnirestException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    public String getFileUrl(String cid) {
        return IPFS_URL + cid;
    }
}
