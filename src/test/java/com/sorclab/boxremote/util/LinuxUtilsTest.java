package com.sorclab.boxremote.util;

import com.sorclab.boxremote.TestFixture;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LinuxUtilsTest
{
    @Mock
    private ProcessUtils processUtils;

    @Mock
    private Process process;

    @InjectMocks
    private LinuxUtils linuxUtils;

    @Test
    public void shellExec_Success() throws IOException
    {
        // Arrange
        String[] cmd = new String[]{"command"};
        File cmdResult = ResourceUtils.getFile("classpath:CmdResult.txt");

        when(processUtils.createProcess(any(String[].class))).thenReturn(process);
        when(process.getInputStream()).thenReturn(TestFixture.getFileInputStream(cmdResult));

        // Act
        List<String> shellExec = linuxUtils.shellExec(cmd);

        // Assert
        assertThat(shellExec).hasSize(2);
        assertThat(shellExec.get(0)).isEqualTo("first line");
        assertThat(shellExec.get(1)).isEqualTo("second line");
    }
}
