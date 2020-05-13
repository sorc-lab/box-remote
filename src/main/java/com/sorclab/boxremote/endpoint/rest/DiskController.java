package com.sorclab.boxremote.endpoint.rest;

import com.sorclab.boxremote.exception.InternalServerError;
import com.sorclab.boxremote.util.LinuxUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class DiskController
{
    @GetMapping(value = "/disk/space")
    public List<String> diskSpace()
    {
        try {
            return LinuxUtils.shellExec(new String[]{"df", "-h"});
        } catch (IOException e) {
            throw new InternalServerError("Command failed", e);
        }
    }
}
