package com.sorclab.boxremote.archive;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
public class ArchiveCtrl
{
    @PostMapping(value = "/archive")
    public String createArchive(@RequestBody ArchiveDto archiveDto) throws IOException
    {
        String src = sanitizePath(archiveDto.getSyncTarget() + archiveDto.getSyncSrc());
        String target = System.getProperty("user.home") + "/Content_" + getTimestamp() + ".zip";

        // TODO: Validate paths, return 400 etc.

        // https://stackoverflow.com/questions/46668418/microservice-return-response-first-and-then-process-the-request
        // TODO: Run zip in separate thread and return 200 status w/ content zip target

        zip(src, target);

        // TODO: Copy zip to archive targets and email user on completion. Also do in separate thread

        return target; // TODO: Rename these vars?
    }

    private String sanitizePath(String path)
    {
        // Remove "./" from path if it exists
        if (path.contains("./"))
            path = path.replaceAll(Pattern.quote("./"), "");

        // Convert "~/" to "user.home" env var
        if (path.contains("~/"))
            path = path.replaceAll(Pattern.quote("~/"), System.getProperty("user.home") + "/");

        return path;
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
             Stream<Path> paths = Files.walk(sourceDirPath))
        {
            paths.filter(path -> !Files.isDirectory(path)).forEach(path -> {
                ZipEntry zipEntry = new ZipEntry(sourceDirPath.relativize(path).toString());

                try {
                    zipOutputStream.putNextEntry(zipEntry);
                    Files.copy(path, zipOutputStream);
                    zipOutputStream.closeEntry();
                }
                catch (IOException e) {
                    System.err.println(e); // TODO: Promote to custom exception
                }
            });
        }

        System.out.println("Zip is created at : "+zipFile);
    }
}
