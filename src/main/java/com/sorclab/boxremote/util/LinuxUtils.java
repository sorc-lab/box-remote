package com.sorclab.boxremote.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LinuxUtils
{
    private final ProcessUtils processUtils;

    public List<String> shellExec(String[] cmd) throws IOException {
        return getProcessResult(processUtils.createProcess(cmd));
    }

    private List<String> getProcessResult(Process process) throws IOException
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
