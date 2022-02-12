package com.ceasdev.archivespicker.data.helper;
import com.ceasdev.archivespicker.FilterType;
import com.ceasdev.archivespicker.PickerPreferences;
import com.ceasdev.archivespicker.utils.FileUtil;
import java.io.File;
import java.io.FileFilter;
import java.util.List;

public class ArchivesFileFilter implements FileFilter{

    private List<String> filterList;
    private FilterType filterType;
    private boolean allowHiddenFiles;

    public ArchivesFileFilter(PickerPreferences preferences){
        filterList = preferences.getFilterList();
        filterType = preferences.getFilterType();
        allowHiddenFiles = preferences.isHiddenArchives();
    }


    @Override
    public boolean accept(File file){
        if(!file.canRead()) return false;
        if(file.isHidden() && !allowHiddenFiles) return false;
        if(filterType == null || filterList == null || filterList.size() <= 0) return true;
        if(file.isDirectory()) return true;
        if(filterType == FilterType.EXTENSION)
            return filterWithExtension(file); 
        else if(filterType == FilterType.MIME_TYPE)
            return filterWithMimeType(file);
        else
            return false;
    }


    private boolean filterWithExtension(File file){
        String extension = FileUtil.getExtension(file);
        if(extension == null || extension.isEmpty()) return false;
        return filterList.contains(extension);
    }


    private boolean filterWithMimeType(File file){
        String mimeType = FileUtil.getMimeType(file);
        if(mimeType == null || mimeType.isEmpty()) return false;
        return filterList.contains(mimeType);
    }


}
