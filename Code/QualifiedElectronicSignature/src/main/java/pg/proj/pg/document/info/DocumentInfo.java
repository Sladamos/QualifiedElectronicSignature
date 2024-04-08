package pg.proj.pg.document.info;

import pg.proj.pg.author.info.AuthorInfo;
import pg.proj.pg.document.details.DocumentDetails;

import java.time.Instant;
import java.time.LocalDateTime;

public record DocumentInfo(DocumentDetails documentDetails, AuthorInfo authorInfo, Instant documentDateTime) {
}
