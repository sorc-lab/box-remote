package com.sorclab.boxremote.endpoint.rest;

import com.sorclab.boxremote.exception.InternalServerError;
import com.sorclab.boxremote.util.LinuxUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

// TODO: Logs are completely silent. 500 w/ "Command failed" prints 0 stack traces--proper status is returned to client

@RestController
public class DiskController
{
    @GetMapping(value = "/disk/space")
    public List<String> getDiskSpace()
    {
        try {
            return LinuxUtils.shellExec(new String[]{"df", "-h"});
        } catch (IOException e) {
            throw new InternalServerError("Command failed", e);
        }
    }

    @GetMapping(value = "/disk/io")
    public List<String> getDiskIO()
    {
        try {
            //return LinuxUtils.shellExec(new String[]{"top", "-n 1", "-b"});
            return LinuxUtils.shellExec(new String[]{"iostat"});
        } catch (IOException e) {
            throw new InternalServerError("Command failed", e);
        }
    }

    @GetMapping(value = "/disk/dirsize")
    public List<String> getDirSize()
    {
        try {
            return LinuxUtils.shellExec(new String[]{"cd", "/var", "&&", "sudo", "du", "-shc", "."});
            //return LinuxUtils.shellExec(new String[]{"ls"});
        } catch (IOException e) {
            e.printStackTrace(); // TODO: Remove after fix to below issue
            throw new InternalServerError("Command failed", e); // TODO: Why is this swallowing the stack trace?
        }
    }
}
