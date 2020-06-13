package com.sorclab.boxremote.disk;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class DiskCtrl
{
    private final DiskSvc diskSvc;

    @GetMapping(value = "/disk/space")
    public List<String> getDiskSpace() {
        return diskSvc.getDiskSpace();
    }

    @GetMapping(value = "/disk/io")
    public List<String> getDiskIo() {
        return diskSvc.getDiskIo();
    }

    @GetMapping(value = "/disk/dirstat")
    public List<String> getDirStat(@RequestParam(name = "path") String path) {
        return diskSvc.getDirStat(path);
    }
}
