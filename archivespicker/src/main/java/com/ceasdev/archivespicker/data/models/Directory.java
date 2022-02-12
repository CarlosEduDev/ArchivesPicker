package com.ceasdev.archivespicker.data.models;

import java.io.File;

public class Directory {

    private String name;
    private File file;

    public Directory(String name, File file){
        this.name = name;
        this.file = file;
    }

    public Directory(){}

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setFile(File file){
        this.file = file;
    }

    public File getFile(){
        return file;
    }


}
