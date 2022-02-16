package com.ceasdev.archivespicker;
import android.app.Activity;
import android.content.Intent;
import com.ceasdev.archivespicker.ui.archives.ArchivesPickerActivity;

public class ArchivesPicker{

    private PickerPreferences pickerPreferences;
    private Activity activity;
    private Intent intent;

    public final static int REQUEST_ARCHIVES_PICKER = 1999;
    public final static String KEY_STRING_ARRAYLIST_EXTRA = "Archives";

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


    public void forResult(){
        forResult(REQUEST_ARCHIVES_PICKER);
    }

    public void forResult(int requestCode){
        activity.startActivityForResult(
            intent.putExtra(PickerPreferences.KEY, pickerPreferences),
            requestCode);
    }

}
