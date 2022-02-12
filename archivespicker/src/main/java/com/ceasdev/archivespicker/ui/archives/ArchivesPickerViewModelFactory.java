package com.ceasdev.archivespicker.ui.archives;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.ceasdev.archivespicker.PickerPreferences;

public class ArchivesPickerViewModelFactory implements ViewModelProvider.Factory{
    
    public PickerPreferences pickerPreferences;

    public ArchivesPickerViewModelFactory(PickerPreferences pickerPreferences){
        this.pickerPreferences = pickerPreferences;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> viewModelClass){
        if(viewModelClass.isAssignableFrom(ArchivesPickerViewModel.class)){
            return (T) new ArchivesPickerViewModel(pickerPreferences);
        }
        throw new IllegalArgumentException("the class is not a ArchivesPickerViewModel instance");
    }

    
    
    
}
