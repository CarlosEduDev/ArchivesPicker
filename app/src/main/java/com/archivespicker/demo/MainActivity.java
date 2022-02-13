package com.archivespicker.demo;
 
import android.app.Activity;
import android.os.Bundle;
import com.ceasdev.archivespicker.ArchivesPicker;
import com.ceasdev.archivespicker.PickerPreferences;
import android.widget.Toast;
import java.io.File;

public class MainActivity extends Activity { 
     
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        ArchivesPicker.newInstance(this, new PickerPreferences.Builder()
        .setHiddenArchives(true)
        .setMaxSelectFiles(3)
        .build())
        .startPickerForResultOK();
        
    }
	
} 
