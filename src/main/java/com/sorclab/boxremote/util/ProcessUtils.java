package com.sorclab.boxremote.util;

import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
class ProcessUtils
{
    Process createProcess(String[] cmd) throws IOException
    {
        ProcessBuilder processBuilder = new ProcessBuilder(cmd);
        processBuilder.redirectErrorStream(true);

        return processBuilder.start();
    }
}
