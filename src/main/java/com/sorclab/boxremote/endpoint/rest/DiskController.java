package com.sorclab.boxremote.endpoint.rest;

import com.sorclab.boxremote.model.DirectoryDTO;
import com.sorclab.boxremote.service.DiskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class DiskController
{
    private final DiskService diskService;

    @GetMapping(value = "/disk/space")
    public List<String> getDiskSpace() {
        return diskService.getDiskSpace();
    }

    @GetMapping(value = "/disk/io")
    public List<String> getDiskIO() {
        return diskService.getDiskIO();
    }

    @GetMapping(value = "/disk/dirstat")
    public List<String> getDirStat(@RequestBody DirectoryDTO directoryDTO) {
        return diskService.getDirStat(directoryDTO);
    }
}
