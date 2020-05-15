package com.sorclab.boxremote.service;

import com.sorclab.boxremote.util.LinuxUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DiskService
{
    private static final String CMD_FAIL = "Command failed";

    private final LinuxUtils linuxUtils;

    public List<String> getDiskSpace()
    {
        try {
            return linuxUtils.shellExec(new String[]{"df", "-h"});
        } catch (IOException e) {
            log.error(CMD_FAIL, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, CMD_FAIL, e);
        }
    }

    public List<String> getDiskIO()
    {
        try {
            //return LinuxUtils.shellExec(new String[]{"top", "-n 1", "-b"});
            // TODO: Add comment here to install iostat and add to readme
            return linuxUtils.shellExec(new String[]{"iostat"});
        } catch (IOException e) {
            log.error(CMD_FAIL, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, CMD_FAIL, e);
        }
    }

    public List<String> getDirStat(String path)
    {
        try {
            // TODO: $ curl -sf -L https://static.rust-lang.org/rustup.sh | sh
            // TODO: $ curl -f -L https://static.rust-lang.org/rustup.sh -O
            // TODO: $ sh rustup.sh
            // TODO: $ curl -LSfs https://japaric.github.io/trust/install.sh | sh -s -- --git vmchale/tin-summer

            // NOTE: Above didn't work. I've installed rust, so just try `cargo install tin-summer`
            // NOTE: Make sure that installing rust is necessary vs using cargo to install tin-summer

            return linuxUtils.shellExec(new String[] {
                    "/bin/sh",
                    "-c",
                    String.format("cd %s; ~/.cargo/bin/sn l", path)
            });
        }
        catch (IOException e)
        {
            log.error(CMD_FAIL, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, CMD_FAIL, e);
        }
    }
}
