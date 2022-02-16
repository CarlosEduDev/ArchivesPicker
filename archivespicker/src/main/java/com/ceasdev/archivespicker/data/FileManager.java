package com.ceasdev.archivespicker.data;
import android.os.Parcelable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.ceasdev.archivespicker.PickerPreferences;
import com.ceasdev.archivespicker.data.models.Archive;
import com.ceasdev.archivespicker.data.models.Directory;
import com.ceasdev.archivespicker.data.models.StateListArchives;
import com.ceasdev.archivespicker.data.works.ListArchivesWork;
import com.ceasdev.archivespicker.data.works.abstraction.OnListArchivesListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Boolean.TRUE;
import static java.lang.Boolean.FALSE;

public class FileManager implements OnListArchivesListener{

    private MutableLiveData<Void> mutableListArchivesPrepare = new MutableLiveData<>();
    private MutableLiveData<Integer[]> mutableListArchivesProgress = new MutableLiveData<>();
    private MutableLiveData<List<Archive>> mutableListArchivesComplete = new MutableLiveData<>();
    private MutableLiveData<Exception> mutableListArchivesFailure = new MutableLiveData<>();
    private MutableLiveData<List<Directory>> mutableListDiretory = new MutableLiveData<>();
    private MutableLiveData<StateListArchives> mutableStateListArchives = new MutableLiveData<>();
   

    private PickerPreferences pickerPreferences;
    private List<StateListArchives> listState;
    private File currentDiretory;
    private ListArchivesWork listArchivesWork;

    public FileManager(PickerPreferences pickerPreferences){
        this.pickerPreferences = pickerPreferences;
        init();
    }


    @Override
    public void onListArchivesPrepare(){
        mutableListArchivesPrepare.postValue(null);
    }

    @Override
    public void onListArchivesProgress(int progress, int progressMax){
        mutableListArchivesProgress.postValue(new Integer[]{progress, progressMax});
    }

    @Override
    public void onListArchivesComplete(List<Archive> listArchives){
        mutableListDiretory.postValue(diretoryToList(currentDiretory.getAbsolutePath()));
        mutableListArchivesComplete.postValue(listArchives);
        mutableStateListArchives.postValue(getStateModelCurrent());
    }

    @Override
    public void onListArchivesFailure(Exception exception){
        mutableListArchivesFailure.postValue(exception);
    }


    private void init(){
        listState = new ArrayList<>();
        currentDiretory = pickerPreferences.getRootDiretory();
        listArchivesWork = new ListArchivesWork(this);
    }

    /**Manager**/

    public PickerPreferences getPreferences(){
        return pickerPreferences;
    }

    public File getDiretory(){
        return currentDiretory;
    }

    public boolean isRootDiretory(){
        return currentDiretory.equals(pickerPreferences.getRootDiretory());
    }


    public boolean isCurrentDirectoryExists(){
        if(currentDiretory == null) return false;
        return currentDiretory.exists();
    }


    public boolean isCurrentDirectoryAccessible(){
        if(currentDiretory == null) return false;
        return currentDiretory.canRead();
    }

    public boolean isEnableHiddenIcon(){
        return pickerPreferences.isEnableHiddenIcon();
    }

    public boolean isShowHiddenArchives(){
        return pickerPreferences.isHiddenArchives();
    }

    public void showHiddenArchives(){
        pickerPreferences.setHiddenArchives(TRUE);
    }

    public void goneHiddenArchives(){
        pickerPreferences.setHiddenArchives(FALSE);
    }


    public void notifyDirectory(){
        if(currentDiretory == null) currentDiretory = pickerPreferences.getRootDiretory();
        listArchivesWork.setPickerPreferences(pickerPreferences);
        listArchivesWork.execute(currentDiretory);
    }


    public boolean nextDirectory(File nextDiretory){
        if(nextDiretory == null || nextDiretory.isFile()) return FALSE;
        currentDiretory = nextDiretory;
        return TRUE;
    }


    public boolean previousDirectory(){
        if(currentDiretory.equals(pickerPreferences.getRootDiretory())) return FALSE;
        File parentFile = currentDiretory.getParentFile();
        if(!parentFile.canRead() && parentFile.isDirectory()) return FALSE;
        currentDiretory = parentFile;
        return TRUE;
    }


    public boolean isRunningListArchivesWork(){
        return listArchivesWork.isWorking();
    }
    
    


    private List<Directory> diretoryToList(String diretoryString){
        List<Directory> listDiretory = new ArrayList<>();
        String diretoryStringFormat = diretoryString.replaceFirst(pickerPreferences.getRootDiretory().getAbsolutePath(), pickerPreferences.getRootDiretoryName());
        String[] nameList = diretoryStringFormat.split(File.separator);
        int maxPosition = nameList.length - 1;
        int position = 0;
        while(position < nameList.length){
            if(nameList [position] == null || nameList [position].isEmpty()) continue;
            listDiretory.add(new Directory(nameList [position],
                    getParentFile(diretoryString, (maxPosition - position))));
            position++;
        }
        return listDiretory;
    }

    private static File getParentFile(String diretory, int count){
        File auxFile = new File(diretory);
        for(int i = 0; i < count; i++)
            auxFile = auxFile.getParentFile();
        return auxFile;
    }

    /**State**/

    public void saveState(int position, Parcelable parcelable){
        listState.add(new StateListArchives(currentDiretory, position, parcelable, isShowHiddenArchives()));
    }


    public StateListArchives getStateModelCurrent(){
        for(StateListArchives state : listState)
            if(state.getFile().equals(currentDiretory) && state.isShowHiddenFiles() == isShowHiddenArchives()){
                if(state.getFile().equals(pickerPreferences.getRootDiretory())) listState.clear();
                else listState.remove(state);
                return state;
            }
        return null;
    }


    /**LiveData**/

    public LiveData<Void> getLiveDataPrepareArchives(){
        return mutableListArchivesPrepare;
    }

    public LiveData<Integer[]> getLiveDataProgressValues(){
        return mutableListArchivesProgress;
    }

    public LiveData<List<Archive>> getLiveDataCompleteArchives(){
        return mutableListArchivesComplete;
    }

    public LiveData<Exception> getLiveDataFailureException(){
        return mutableListArchivesFailure;
    }

    public LiveData<List<Directory>> getLiveDataListDirectory(){
        return mutableListDiretory;
    }

    public LiveData<StateListArchives> getLiveDataStateListArchives(){
        return mutableStateListArchives;
    }


}
