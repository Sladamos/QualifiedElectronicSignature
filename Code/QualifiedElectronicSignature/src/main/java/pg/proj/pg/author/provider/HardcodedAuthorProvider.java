package pg.proj.pg.author.provider;

import lombok.Getter;
import pg.proj.pg.author.info.AuthorInfo;

@Getter
public class HardcodedAuthorProvider implements AuthorProvider {

    private final AuthorInfo author = new AuthorInfo("Sławomir Adamowicz & Karol Felskowski");
}
