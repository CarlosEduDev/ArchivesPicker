package com.ceasdev.archivespicker;
import android.app.Activity;
import android.content.Intent;
import com.ceasdev.archivespicker.ui.archives.ArchivesPickerActivity;

public class ArchivesPicker {
    
    private PickerPreferences pickerPreferences;
    private Activity activity;
    private Intent intent;

    private ArchivesPicker(PickerPreferences pickerPreferences, Activity activity){
        this.pickerPreferences = pickerPreferences;
        this.activity = activity;
        this.intent = new Intent(activity, ArchivesPickerActivity.class);
    }
    
    public static ArchivesPicker newInstance(Activity activity){
        return new ArchivesPicker(new PickerPreferences.Builder().build(), activity);
    }
    
    public static ArchivesPicker newInstance(Activity activity, PickerPreferences pickerPreferences){
        return new ArchivesPicker(pickerPreferences, activity);
    }
    
    
    public void startPickerForResultOK(){
        intent.putExtra(PickerPreferences.KEY, pickerPreferences);
        activity.startActivityForResult(intent, Activity.RESULT_OK);
    }
    
    
}
