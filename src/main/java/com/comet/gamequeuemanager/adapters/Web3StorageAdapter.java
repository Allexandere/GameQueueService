package com.comet.gamequeuemanager.adapters;

import com.comet.gamequeuemanager.dto.CidDto;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class Web3StorageAdapter {

    private final static String AUTHORIZATION_HEADER = "Authorization";
    private final static String FILE_PARAMETER_NAME = "file";
    private final static String TOKEN_PREFIX = "Bearer ";

    @Autowired
    private Gson gson;

    @Value("${web3.storage.token}")
    private String token;
    @Value("${web3.storage.api.prefix}")
    private String apiUrl;
    @Value("${web3.storage.api.upload-endpoint}")
    private String uploadEndpoint;

    public String uploadFile(File file) throws UnirestException {
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.post(apiUrl + uploadEndpoint)
                .header(AUTHORIZATION_HEADER, TOKEN_PREFIX + token)
                .field(FILE_PARAMETER_NAME, file)
                .asString();
        return gson.fromJson(response.getBody(), CidDto.class).getCid();
    }
}
