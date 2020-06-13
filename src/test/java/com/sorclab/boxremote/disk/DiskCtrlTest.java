package com.sorclab.boxremote.disk;

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

// TODO: Replace with MockMvc
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DiskCtrlTest
{
    @Autowired
    private TestRestTemplate testRestTemplate;

    @MockBean
    private LinuxUtils linuxUtils;

    @Test
    public void getDiskSpace_Returns200() throws IOException
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
    public void getDiskSpace_CmdFail_Returns500() throws IOException
    {
        // Arrange
        when(linuxUtils.shellExec(any(String[].class))).thenThrow(IOException.class);

        // Act
        ResponseEntity<?> response = testRestTemplate.getForEntity("/disk/space", Object.class);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    public void getDiskIo_Returns200() throws IOException
    {
        // Arrange
        when(linuxUtils.shellExec(any(String[].class))).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<?> response = testRestTemplate.getForEntity("/disk/io", Object.class);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    public void getDiskIo_CmdFail_Returns500() throws IOException
    {
        // Arrange
        when(linuxUtils.shellExec(any(String[].class))).thenThrow(IOException.class);

        // Act
        ResponseEntity<?> response = testRestTemplate.getForEntity("/disk/io", Object.class);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    public void getDirStat_Returns200() throws IOException
    {
        // Arrange
        when(linuxUtils.shellExec(any(String[].class))).thenReturn(Collections.emptyList());

        ResponseEntity<?> response = testRestTemplate.getForEntity(
                "/disk/dirstat?path=/path/to/dir",
                Object.class);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    public void getDirStat_CmdFail_Returns500() throws IOException
    {
        // Arrange
        when(linuxUtils.shellExec(any(String[].class))).thenThrow(IOException.class);

        // Act
        ResponseEntity<?> response = testRestTemplate.getForEntity(
                "/disk/dirstat?path=/path/to/dir",
                Object.class);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).isNotNull();
    }
}
