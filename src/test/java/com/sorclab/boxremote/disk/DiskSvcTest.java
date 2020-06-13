package com.sorclab.boxremote.disk;

import com.sorclab.boxremote.util.LinuxUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DiskSvcTest
{
    @Captor
    private ArgumentCaptor<String[]> argCaptor;

    @Mock
    private LinuxUtils linuxUtils;

    @InjectMocks
    private DiskSvc diskSvc;

    @Test
    public void getDiskSpace_Success() throws Exception
    {
        // Arrange
        when(linuxUtils.shellExec(any(String[].class))).thenReturn(Collections.emptyList());

        // Act
        diskSvc.getDiskSpace();

        // Assert
        verify(linuxUtils, atMostOnce()).shellExec(argCaptor.capture());

        assertThat(argCaptor.getValue()[0]).isEqualTo("df");
        assertThat(argCaptor.getValue()[1]).isEqualTo("-h");
    }

    @Test
    public void getDiskSpace_CmdFail_ThrowsResponseStatusException() throws IOException
    {
        when(linuxUtils.shellExec(any(String[].class))).thenThrow(IOException.class);

        assertThatThrownBy(() -> diskSvc.getDiskSpace())
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Command failed");
    }

    @Test
    public void getDiskIO_Success() throws IOException
    {
        // Arrange
        when(linuxUtils.shellExec(any(String[].class))).thenReturn(Collections.emptyList());

        // Act
        diskSvc.getDiskIo();

        // Assert
        verify(linuxUtils, atMostOnce()).shellExec(argCaptor.capture());
        assertThat(argCaptor.getValue()[0]).isEqualTo("iostat");
    }

    @Test
    public void getDiskIO_CmdFail_ThrowsResponseStatusException() throws IOException
    {
        when(linuxUtils.shellExec(any(String[].class))).thenThrow(IOException.class);

        assertThatThrownBy(() -> diskSvc.getDiskIo())
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Command failed");
    }

    @Test
    public void getDirStat_Success() throws Exception
    {
        // Arrange
        String path = "/path/to/dir";
        when(linuxUtils.shellExec(any(String[].class))).thenReturn(Collections.emptyList());

        // Act
        diskSvc.getDirStat(path);

        // Assert
        verify(linuxUtils, atMostOnce()).shellExec(argCaptor.capture());

        assertThat(argCaptor.getValue()[0]).isEqualTo("/bin/sh");
        assertThat(argCaptor.getValue()[1]).isEqualTo("-c");
        assertThat(argCaptor.getValue()[2])
                .isEqualTo(String.format("cd %s; ~/.cargo/bin/sn l", path));
    }

    @Test
    public void getDirStat_CmdFail_ThrowsResponseStatusException() throws IOException
    {
        String path = "/path/to/dir";
        when(linuxUtils.shellExec(any(String[].class))).thenThrow(IOException.class);

        assertThatThrownBy(() -> diskSvc.getDirStat(path))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Command failed");
    }
}
