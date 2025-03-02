package pg.proj.pg.file;

import org.junit.jupiter.api.Test;
import pg.proj.pg.error.CriticalAppError;
import pg.proj.pg.file.extension.FileExtension;
import pg.proj.pg.file.selector.FileSelector;
import pg.proj.pg.file.selector.JavaFXFileSelector;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThatThrownBy;


public class JavaFXFileSelectorTest {

    @Test
    void shouldThrowCriticalAppErrorOnSelectFileWhenStageIsNotSet(){
        FileSelector selector = new JavaFXFileSelector(null, "Title", Set.of(FileExtension.PDF));
        assertThatThrownBy(selector::selectFile)
                .isInstanceOf(CriticalAppError.class);
    }

    /**
     * It may contain defect masking because stage can't be mocked
     * **/
    @Test
    void should_throwCriticalAppErrorOnSelectFile_when_allowedExtensionsAreEmpty(){
        FileSelector selector = new JavaFXFileSelector(null, "Title", Set.of());
        assertThatThrownBy(selector::selectFile)
                .isInstanceOf(CriticalAppError.class);
    }

}
