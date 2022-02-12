package com.ceasdev.archivespicker.ui.archives;
import com.ceasdev.archivespicker.data.models.Archive;
import java.io.File;

public interface ArchivesPickerNavigator {

    boolean returnFolder();
    
    void openFolder(File file);
    
    void analysisFolder(File fileFolder);
    
    void selectFile(File file);
    
    void updateToolbarTexts(Archive archive);
    
    void updateToolbarIcons();
    
    void progressShow(long delay);
    
    void progressUpdate(int progress, int max);
    
    void progressDismiss();
    
}
