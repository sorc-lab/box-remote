package com.sorclab.boxremote.endpoint.rest;

import com.sorclab.boxremote.util.LinuxUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DiskControllerTest
{
    @Autowired
    private TestRestTemplate testRestTemplate;

    @MockBean
    private LinuxUtils linuxUtils;

    @Test
    public void getDiskSpace_Success() throws IOException
    {
        // Arrange
        when(linuxUtils.shellExec(any(String[].class))).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<?> response = testRestTemplate.getForEntity("/disk/space", Object.class);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    public void getDiskSpace_CmdFail_ThrowsInternalServerError() throws IOException
    {
        // Arrange
        when(linuxUtils.shellExec(any(String[].class))).thenThrow(IOException.class);

        // Act
        ResponseEntity<?> response = testRestTemplate.getForEntity("/disk/space", Object.class);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).isNotNull();
    }
}
