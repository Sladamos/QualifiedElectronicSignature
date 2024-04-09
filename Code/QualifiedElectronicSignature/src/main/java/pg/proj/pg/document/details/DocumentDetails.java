package pg.proj.pg.document.details;

import pg.proj.pg.file.extension.FileExtension;

import java.time.Instant;

public record DocumentDetails(long size, Instant creationDate, Instant modificationDate,
                              String fileName, FileExtension fileExtension) {

    @Override
    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }

        if(!(obj instanceof DocumentDetails other)) {
            return false;
        }

        return other.size == size
                && other.creationDate.equals(creationDate)
                && other.modificationDate.equals(modificationDate)
                && other.fileName.equals(fileName)
                && other.fileExtension.equals(fileExtension);
    }
}
