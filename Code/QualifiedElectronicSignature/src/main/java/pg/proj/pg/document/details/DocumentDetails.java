package pg.proj.pg.document.details;

import lombok.Builder;
import pg.proj.pg.file.extension.FileExtension;

import java.time.Instant;

@Builder
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
