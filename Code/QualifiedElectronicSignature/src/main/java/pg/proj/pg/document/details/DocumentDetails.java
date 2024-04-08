package pg.proj.pg.document.details;

import pg.proj.pg.file.info.FileInfo;

import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.time.LocalDateTime;

public record DocumentDetails(long size, Instant creationDate, Instant modificationDate, FileInfo fileInfo) {
}
