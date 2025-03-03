package pg.proj.pg.author.provider;

import org.junit.jupiter.api.Test;
import pg.proj.pg.author.info.AuthorInfo;

import static org.assertj.core.api.Assertions.assertThat;

class HardcodedAuthorProviderTest {

    private final HardcodedAuthorProvider uut = new HardcodedAuthorProvider();

    @Test
    public void shouldReturnValidAuthor() {
        AuthorInfo validAuthor = new AuthorInfo("Sławomir Adamowicz");
        assertThat(uut.getAuthor()).isEqualTo(validAuthor);
    }

}