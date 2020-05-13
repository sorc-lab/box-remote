package com.sorclab.boxremote.util;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class LinuxUtils
{
    public static List<String> shellExec(String[] cmd) throws IOException {
        return getProcessResult(createProcess(cmd));
    }

    private static Process createProcess(String[] cmd) throws IOException
    {
        ProcessBuilder processBuilder = new ProcessBuilder(cmd);
        processBuilder.redirectErrorStream(true);

        return processBuilder.start();
    }

    private static List<String> getProcessResult(Process process) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        List<String> result = new ArrayList<>();

        String line;

        while (true)
        {
            line = reader.readLine();
            if (line == null) break;

            result.add(line);
        }

        return result;
    }
}
