package com.sorclab.boxremote.endpoint.rest;

import com.sorclab.boxremote.exception.InternalServerError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@RestController
public class DiskController
{
    @GetMapping(value = "/disk/space")
    public List<String> diskSpace()
    {
        try {
            return cmdResultOutput(new String[]{"df", "-h"});
        } catch (IOException e) {
            throw new InternalServerError("Command failed", e);
        }
    }

    private List<String> cmdResultOutput(String[] cmd) throws IOException
    {
        ProcessBuilder processBuilder = new ProcessBuilder(cmd);
        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        List<String> cmdResult = new ArrayList<>();

        String line;

        while (true)
        {
            line = reader.readLine();
            if (line == null) break;

            cmdResult.add(line);
        }

        return cmdResult;
    }
}
