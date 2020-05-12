package com.sorclab.boxremote.endpoint.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@RestController
public class DiskController {
    // TODO: Rename to something simpler like getDiskstats or just diskstats, since "get" is implied with annotation
    @GetMapping(value = "/") // TODO: Define API paths
    public List<String> getDiskSpaceStats() throws IOException {
        //ProcessBuilder processBuilder = new ProcessBuilder("df", "-h");
        ProcessBuilder processBuilder = new ProcessBuilder("top", "-n 1", "-b");

        processBuilder.redirectErrorStream(true);

        Process p = processBuilder.start();

        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));

        List<String> cmdResult = new ArrayList<>();
        String line;

        while (true) {
            line = r.readLine();

            if (line == null) break;

            cmdResult.add(line);
        }

        // TODO: Make service class that returns the array or errors based on cmd error code != 0 etc.
        return cmdResult;
    }

    @GetMapping(value = "/bytes")
    public String getBytes() throws IOException
    {
//        public static void outputStreamWriter(String file, String data) throws IOException {
//        try (OutputStream out = new FileOutputStream(file);
//             Writer writer = new OutputStreamWriter(out,"UTF-8")) {
//            writer.write(data);
//        }

        ProcessBuilder processBuilder = new ProcessBuilder("top");

        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        return "";
    }

    // LIST OUT POSSIBLE CONSUMER NEEDS
    // - Disk space stats
    // - Disk io usage
    // - Size of directories by folder and file, i.e. file tree output
    // - CPU and memory usage of Dropbox
    // - Dropbox filesync status of a given directory
    // - List dropbox folder and its directories, essentially an ls with given dirs etc.
    // - Overall Dropbox daemon status
    // - Restart dropbox daemon
    // - Stop dropbox daemon
    // - Shutdown entire system. (kills dropbox daemon and shutdown the system.)
    // - Check integrity of Content backups, both archival and non-archival. See scripts.
    // - Download any given files. Allows DL from network vs. Dropbox cloud.
    // NOTE: Do we want write access via update or just read only? If we do up/down then we can close SSH 100%. Secure?
}
