package com.sorclab.boxremote;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class TestFixture
{
    public static InputStream getFileInputStream(File file) throws FileNotFoundException {
        return new FileInputStream(file);
    }

    public static HttpEntity getHttpEntity()
    {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        return new HttpEntity<>(null, httpHeaders);
    }
}
