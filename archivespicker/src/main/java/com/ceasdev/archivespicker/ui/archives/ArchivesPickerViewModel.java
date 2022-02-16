package com.ceasdev.archivespicker.ui.archives;
import android.os.Parcelable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.ceasdev.archivespicker.PickerPreferences;
import com.ceasdev.archivespicker.data.FileManager;
import com.ceasdev.archivespicker.data.models.Archive;
import com.ceasdev.archivespicker.data.models.Directory;
import com.ceasdev.archivespicker.data.models.StateListArchives;
import java.io.File;
import java.util.List;

import static java.lang.Boolean.TRUE;
import static java.lang.Boolean.FALSE;


public class ArchivesPickerViewModel extends ViewModel{

    private FileManager fileManager;

    ArchivesPickerViewModel(PickerPreferences pickerPreferences){
        this.fileManager = new FileManager(pickerPreferences);
    }
   

    public void loadDiretory(File file){
        if(fileManager.nextDirectory(file))
            notifyDirectory();
    }


    public void notifyDirectory(){
        fileManager.notifyDirectory();
    }


    public boolean returnDiretory(){
        if(fileManager.previousDirectory()){
            notifyDirectory();
            return TRUE;
        }else
            return FALSE;  
    }

    

    public void changeHiddenFiles(){
        if(fileManager.isShowHiddenArchives())
            fileManager.goneHiddenArchives();
        else
            fileManager.showHiddenArchives();
    }


    public boolean isLoadingDiretory(){
        return fileManager.isRunningListArchivesWork();
    }
    
    public boolean isEnableHiddenIcon(){
        return fileManager.isEnableHiddenIcon();
    }
    
    public boolean isShowHiddenArchives(){
        return fileManager.isShowHiddenArchives();
    }


    public void saveStateArchives(int position, Parcelable parcelable){
        fileManager.saveState(position, parcelable);
    }

    
    public int getMaxSelectFiles(){
        return fileManager.getPreferences().getMaxSelectFiles();
    }

    /**LiveData**/

    public LiveData<Void> getLiveDataPrepare(){
        return fileManager.getLiveDataPrepareArchives();
    }

    public LiveData<List<Archive>> getLiveDataCompleteArchives(){
        return fileManager.getLiveDataCompleteArchives();
    }

    public LiveData<Integer[]> getLiveDataProgressValues(){
        return fileManager.getLiveDataProgressValues();
    }

    public LiveData<Exception> getLiveDataFailureException(){
        return fileManager.getLiveDataFailureException();
    }

    public LiveData<List<Directory>> getLiveDataListDirectory(){
        return fileManager.getLiveDataListDirectory();
    }

    public LiveData<StateListArchives> getLiveDataStateListArchives(){
        return fileManager.getLiveDataStateListArchives();
    }
    
}
