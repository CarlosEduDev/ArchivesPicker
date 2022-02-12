package com.ceasdev.archivespicker.data;

public enum FileType{
    IMAGE("image"), AUDIO("audio"), VIDEO("video"),
    TEXT("text"), APK("vnd.android.package-archive"),
    UNKNOWN("");

    private String type;

    private FileType(String type){
        this.type = type; 
    }

    public String getStringType(){
        return type;
    }
}
