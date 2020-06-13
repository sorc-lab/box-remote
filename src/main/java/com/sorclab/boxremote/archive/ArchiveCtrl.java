package com.sorclab.boxremote.archive;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
public class ArchiveCtrl
{
    // https://www.techgeeknext.com/java8/java-zip-file-folder-example

    @PostMapping(value = "/archive")
    @ResponseStatus(HttpStatus.OK)
    public void createArchive(@RequestBody ArchiveDto archiveDto) throws IOException
    {
//        // Remove './' from path if it exists
//        String syncSrc = archiveDto.getSyncSrc();
//        if (archiveDto.getSyncSrc().indexOf("./") == 0)
//            syncSrc = archiveDto.getSyncSrc().substring(2);
//
//        // Strip everything before ":", i.e. user@<ip>:<path>.
//        String syncTarget = archiveDto.getSyncTarget()
//                .substring(archiveDto.getSyncTarget().indexOf(":") + 1);
//
//        String src = syncTarget + syncSrc;
//        String target = "~/Content_" + getTimestamp() + ".zip";

        String src = System.getProperty("user.home") + "/Dropbox";
        String target = System.getProperty("user.home") + "/Content_" + getTimestamp() + ".zip";

        System.out.println();
        System.out.println("SRC: " + src);
        System.out.println("TARGET: " + target);

        zip(src, target);

        // - Parse DTO and get src and target dirs
        // - Loop targets and zip to those locations
                // Validate src and targets. Check that dir size is less than available space on target drive.
        // - Email the user if success or fail
        // - return list of targets to api consumer. Leave it to the client to communicate to the
        // user what is going on asynchronously on the server.

        // NOTE: Don't call async from client. Just handle the request and return 200, then do stuff in background.
        // https://stackoverflow.com/questions/46668418/microservice-return-response-first-and-then-process-the-request
    }

    // yyyy_MM_dd_<seconds since unix epoch>
    private String getTimestamp()
    {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy_MM_dd");
        LocalDateTime localDateTime = LocalDateTime.now();
        return String.format(
                "%s_%s",
                format.format(localDateTime),
                Instant.now().getEpochSecond());
    }

    private void zip(String srcDirPath, String targetDirPath) throws IOException
    {
        Path zipFile = Files.createFile(Paths.get(targetDirPath));

        Path sourceDirPath = Paths.get(srcDirPath);
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(Files.newOutputStream(zipFile));
             Stream<Path> paths = Files.walk(sourceDirPath)) {
            paths
                    .filter(path -> !Files.isDirectory(path))
                    .forEach(path -> {
                        ZipEntry zipEntry = new ZipEntry(sourceDirPath.relativize(path).toString());
                        try {
                            zipOutputStream.putNextEntry(zipEntry);
                            Files.copy(path, zipOutputStream);
                            zipOutputStream.closeEntry();
                        } catch (IOException e) {
                            System.err.println(e);
                        }
                    });
        }

        System.out.println("Zip is created at : "+zipFile);
    }
}
