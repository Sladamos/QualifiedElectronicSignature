package pg.proj.pg.iv.extractor;

import pg.proj.pg.iv.InitializationVector;

public interface InitializationVectorExtractor {
    InitializationVector extractIvFromArray(byte[] arr);
}
