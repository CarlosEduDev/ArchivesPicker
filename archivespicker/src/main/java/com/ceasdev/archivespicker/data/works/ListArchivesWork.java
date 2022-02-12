package com.ceasdev.archivespicker.data.works;

import com.ceasdev.archivespicker.PickerPreferences;
import com.ceasdev.archivespicker.data.helper.ArchivesFileFilter;
import com.ceasdev.archivespicker.data.models.Archive;
import com.ceasdev.archivespicker.data.works.abstraction.OnListArchivesListener;
import com.ceasdev.archivespicker.data.works.abstraction.SimpleWork;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.IOException;

public class ListArchivesWork extends SimpleWork<File, Integer, List<Archive>>{

    
    private PickerPreferences pickerPreferences;
    private OnListArchivesListener onListArchivesListener;

    public ListArchivesWork(OnListArchivesListener onListArchivesListener){
        this.onListArchivesListener = onListArchivesListener;
    }

    

    @Override
    protected void onPrepareWork(){
    onListArchivesListener.onListArchivesPrepare();
    }


    @Override
    protected List<Archive> doWorkInBackground(File[] params) throws Exception{
        int position = 0; //Auxiliar
        final List<Archive> 
            filesList = new ArrayList<>(),
            foldersList = new ArrayList<>(),
            allList = new ArrayList<>();
        final File[] fileArray = params[0].listFiles(new ArchivesFileFilter(pickerPreferences));

        if(fileArray == null || fileArray.length == 0) return allList;

        for(File file : fileArray){
            if(file.isDirectory())
                foldersList.add(new Archive(file));
            else if(file.isFile()) 
                filesList.add(new Archive(file));
            else continue;
            postProgressWork(position++, fileArray.length - 1);
        }

        //Ordem alfabetica
        Collections.sort(foldersList);
        Collections.sort(filesList);
        
        allList.addAll(foldersList);
        allList.addAll(filesList);

        return allList;
    }

    @Override
    protected void onResultWork(List<Archive> result){
        onListArchivesListener.onListArchivesComplete(result);
    }

    @Override
    protected void onPostProgressWork(Integer[] post){
        onListArchivesListener.onListArchivesProgress(post[0], post[1]);
    }
    

    @Override
    protected void onFailureWork(Exception exception){
       onListArchivesListener.onListArchivesFailure(exception);
    }
    
 
    public void setPickerPreferences(PickerPreferences pickerPreferences){
        this.pickerPreferences = pickerPreferences;
    }
    
}
