package com.ceasdev.archivespicker.data.models;

import android.os.Parcelable;
import java.io.File;

public class StateListArchives {

    private File file;
    private int position;
    private Parcelable state;
    private boolean showHiddenFiles;

    public StateListArchives(File file, int position, Parcelable state, boolean showHiddenFiles){
        this.file = file;
        this.position = position;
        this.state = state;
        this.showHiddenFiles = showHiddenFiles;
    }

   
    
    public void setFile(File file){
        this.file = file;
    }

    public File getFile(){
        return file;
    }

    public void setPosition(int position){
        this.position = position;
    }

    public int getPosition(){
        return position;
    }

    public void setState(Parcelable state){
        this.state = state;
    }

    public Parcelable getState(){
        return state;
    }
    
    public void setShowHiddenFiles(boolean showHiddenFiles){
        this.showHiddenFiles = showHiddenFiles;
    }

    public boolean isShowHiddenFiles(){
        return showHiddenFiles;
    }
    

}
