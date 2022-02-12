package com.ceasdev.archivespicker;
import android.os.Parcel;
import android.os.Parcelable;
import com.ceasdev.archivespicker.utils.FileUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Boolean.TRUE;
import static java.lang.Boolean.FALSE;

public final class PickerPreferences implements Parcelable{

    /**enableHiddenIcon, quando true o usuario poderar selecionar
     * arquivos ocultos ou não**/
    private boolean enableHiddenIcon;
    /**hiddenArchives, poderá ser alterada caso a
     enableHiddenIcon seja true**/
    private boolean hiddenArchives;
    /**maxSelectFiles, para seleção multipla defina
     maior que 1**/
    private int maxSelectFiles;
    /**filterType, define o tipo de busca do filtro**/
    private FilterType filterType;
    /**filterList, conteúdos para filtragem**/;
    private List<String> filterList;
    /**rootDiretory, pasta raiz o maximo que o usuario poderá retornar
     * e ponto de partida**/
    private File rootDiretory;
    /**rootDiretoryName, sera exibido abaixo da toolbar como
     * nome do diretorio raiz*/
    private String rootDiretoryName;

    private PickerPreferences(){}

    private PickerPreferences(Parcel parcel){
        this.enableHiddenIcon = toBoolean(parcel);
        this.hiddenArchives = toBoolean(parcel);
        this.maxSelectFiles = parcel.readInt();
        this.filterType = FilterType.valueOf(parcel.readString());
        this.filterList = parcel.createStringArrayList();
        this.rootDiretory = (File) parcel.readSerializable();
        this.rootDiretoryName = parcel.readString();
    }


    private PickerPreferences(Builder builder){
        this.enableHiddenIcon = builder.enableHiddenIcon;
        this.hiddenArchives = builder.hiddenArchives;
        this.maxSelectFiles = builder.maxSelectFiles;
        this.filterType = builder.filterType;
        this.filterList = builder.filterList;
        this.rootDiretory = builder.rootDiretory;
        this.rootDiretoryName = builder.rootDiretoryName;
    }


    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flag){
        parcel.writeInt(toInt(enableHiddenIcon));
        parcel.writeInt(toInt(hiddenArchives));
        parcel.writeInt(maxSelectFiles);
        parcel.writeString(filterType.name());
        parcel.writeStringList(new ArrayList<String>(filterList));
        parcel.writeSerializable(rootDiretory);
        parcel.writeString(rootDiretoryName);
    }



    private int toInt(boolean value){
        return value ? 1 : 0;
    }

    private boolean toBoolean(Parcel parcel){
        return parcel.readInt() == 1;
    }



    public void setHiddenArchives(boolean hiddenArchives){
        this.hiddenArchives = hiddenArchives;
    }

    public boolean isEnableHiddenIcon(){
        return enableHiddenIcon;
    }

    public boolean isHiddenArchives(){
        return hiddenArchives;
    }

    public int getMaxSelectFiles(){
        return maxSelectFiles;
    }

    public FilterType getFilterType(){
        return filterType;
    }

    public List<String> getFilterList(){
        return filterList;
    }
    

    public File getRootDiretory(){
        return rootDiretory;
    }

    public String getRootDiretoryName(){
        return rootDiretoryName;    
    }



    public static class Builder{

        private boolean enableHiddenIcon;
        private boolean hiddenArchives;
        private int maxSelectFiles;
        private FilterType filterType;
        private List<String> filterList;
        private File rootDiretory;
        private String rootDiretoryName;

        //Inicializar valores padroes
        {
            enableHiddenIcon = TRUE;
            hiddenArchives = FALSE;
            maxSelectFiles = 1;
            filterType = FilterType.EXTENSION;
            filterList = new ArrayList<>();
            rootDiretory = FileUtil.getRootDiretory();
            rootDiretoryName = rootDiretory.getName();
        }


        public Builder setEnableHiddenIcon(boolean enableHiddenIcon){
            this.enableHiddenIcon = enableHiddenIcon;
            return this;
        }

        public Builder setHiddenArchives(boolean hiddenArchives){
            this.hiddenArchives = hiddenArchives;
            return this;
        }

        public Builder setMaxSelectFiles(int maxSelectFiles){
            this.maxSelectFiles = maxSelectFiles;
            return this;
        }

        public Builder setFilterType(FilterType filterType){
            this.filterType = filterType;
            return this;
        }

        public Builder setFilterList(List<String> filterList){
            this.filterList = filterList;
            return this;
        }
        
        public Builder setFilterList(String... filterList){
            this.filterList = Arrays.asList(filterList);
            return this;
        }

        public Builder setRootDiretory(File rootDiretory){
            this.rootDiretory = rootDiretory;
            return this;
        }

        public Builder setRootDiretoryName(String rootDiretoryName){
            this.rootDiretoryName = rootDiretoryName;
            return this;
        }
        
        public PickerPreferences build(){
            return new PickerPreferences(this);
        }

    }

    public static final String KEY = "ArchivesPickerPreferences";

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator<PickerPreferences>(){

        @Override
        public PickerPreferences createFromParcel(Parcel parcel){
            return new PickerPreferences(parcel);
        }

        @Override
        public PickerPreferences[] newArray(int size){
            return new PickerPreferences[size];
        }


    };

}
