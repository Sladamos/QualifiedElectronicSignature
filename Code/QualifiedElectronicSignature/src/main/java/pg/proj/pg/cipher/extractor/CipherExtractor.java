package pg.proj.pg.cipher.extractor;

import pg.proj.pg.iv.extractor.InitializationVectorExtractor;
import pg.proj.pg.key.extractor.KeyContentExtractor;

public interface CipherExtractor extends KeyContentExtractor, InitializationVectorExtractor {
}
