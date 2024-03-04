package file;

import org.junit.jupiter.api.Test;
import pg.proj.pg.error.definition.CriticalAppError;
import pg.proj.pg.file.extension.FileExtension;
import pg.proj.pg.file.selector.FileSelector;
import pg.proj.pg.file.selector.JavaFXFileSelector;
import static org.assertj.core.api.Assertions.*;

import java.util.Set;


public class JavaFXFileSelectorTest {

    @Test
    void should_throwCriticalAppErrorOnSelectFile_when_stageIsNotSet(){
        FileSelector selector = new JavaFXFileSelector(null, Set.of(FileExtension.CPP));
        assertThatThrownBy(selector::selectFile)
                .isInstanceOf(CriticalAppError.class);
    }

    /**
     * It may contain defect masking because stage can't be mocked
     * **/
    @Test
    void should_throwCriticalAppErrorOnSelectFile_when_allowedExtensionsAreEmpty(){
        FileSelector selector = new JavaFXFileSelector(null, Set.of());
        assertThatThrownBy(selector::selectFile)
                .isInstanceOf(CriticalAppError.class);
    }

}
