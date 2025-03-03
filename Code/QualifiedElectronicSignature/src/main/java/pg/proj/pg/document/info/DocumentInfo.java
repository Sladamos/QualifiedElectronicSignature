package pg.proj.pg.document.info;

import lombok.Builder;
import pg.proj.pg.author.info.AuthorInfo;
import pg.proj.pg.document.details.DocumentDetails;

import java.time.Instant;

@Builder
public record DocumentInfo(DocumentDetails documentDetails, AuthorInfo authorInfo, Instant documentDateTime) {
    @Override
    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }

        if(!(obj instanceof DocumentInfo other)) {
            return false;
        }

        return other.authorInfo.equals(authorInfo) && other.documentDetails.equals(documentDetails);
    }
}
