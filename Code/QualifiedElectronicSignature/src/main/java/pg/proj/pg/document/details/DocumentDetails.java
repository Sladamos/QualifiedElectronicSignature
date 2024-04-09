package pg.proj.pg.document.details;

import pg.proj.pg.file.extension.FileExtension;

import java.time.Instant;

public record DocumentDetails(long size, Instant creationDate, Instant modificationDate,
                              String fileName, FileExtension extension) {
}
