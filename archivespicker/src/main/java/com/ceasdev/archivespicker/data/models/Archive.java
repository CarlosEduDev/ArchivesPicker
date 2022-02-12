package com.ceasdev.archivespicker.data.models;
import com.ceasdev.archivespicker.data.FileType;
import com.ceasdev.archivespicker.utils.FileUtil;
import com.ceasdev.archivespicker.utils.TimerUtil;
import java.io.File;
import java.util.List;

public class Archive implements Comparable<Archive>{

    private File file;
    private boolean selected;
    /**animateThis, define se o diretorio sera animado
     * quando for notificado**/
    private boolean animateThis;


    public Archive(File file, boolean selected, boolean animateThis){
        this.file = file;
        this.selected = selected;
        this.animateThis = animateThis;
    }

    public Archive(File file, boolean selected){
        this(file, selected, Boolean.FALSE);
    }

    public Archive(File file){
        this(file, Boolean.FALSE, Boolean.FALSE);
    }

    //Ordem alfabetica
    @Override
    public int compareTo(Archive archive){
        return this.getName().compareToIgnoreCase(archive.getName());
    }



    public File getFile(){
        return file;
    }

    public void setSelected(boolean selected){
        this.selected = selected;
    }

    public boolean isSelected(){
        return selected;
    }

    public void setAnimateThis(boolean animateThis){
        this.animateThis = animateThis;
    }

    public boolean isAnimateThis(){
        return animateThis;
    }

    public String getName(){
        return file.getName();
    }

    public String getPath(){
        return file.getAbsolutePath();
    }

    public FileType getFileType(){
        String mimeType = FileUtil.getMimeType(file);
        if(mimeType == null || mimeType.isEmpty()) return FileType.UNKNOWN;
        for(FileType fileType : FileType.values())
            if(mimeType.contains(fileType.getStringType())) return fileType;
        return FileType.UNKNOWN;
    }

    public boolean isDirectory(){
        return file.isDirectory();
    }

    public boolean isFile(){
        return (!isDirectory());
    }
    
    
    public boolean exists(){
        return getFile().exists();
    }
    
    
    public boolean notExists(){
        return !exists();
    }
    

    public long getSize(){
        if(isDirectory()) return 0;
        return file.length();
    }

    public String getSizeString(){
        return FileUtil.getSizeFormated(getSize());
    }


    public long getLastModified(){
        return file.lastModified();
    }

    
    public String getLastModifiedString(){
        return TimerUtil.formatString(getLastModified());
    }

    
    public static int countFiles(List<Archive> listArchive){
        int countFiles = 0;
        if(listArchive == null) return countFiles;
        for(Archive archive : listArchive)
        if(archive.isFile()) countFiles++;
        return countFiles;
    }
    
    public static int countFolders(List<Archive> listArchive){
        int countFolders = 0;
        if(listArchive == null) return countFolders;
        for(Archive archive : listArchive)
            if(archive.isDirectory()) countFolders++;
        return countFolders;
    }

}
