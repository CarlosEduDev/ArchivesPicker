package com.archivespicker.demo;
 
import android.app.Activity;
import android.os.Bundle;
import com.ceasdev.archivespicker.ArchivesPicker;
import com.ceasdev.archivespicker.PickerPreferences;
import android.widget.Toast;
import java.io.File;
import android.content.Intent;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.view.View;
import android.widget.ArrayAdapter;
import com.ceasdev.archivespicker.FilterType;

public class MainActivity extends Activity {
    
    private CheckBox checkBoxIcon, checkBoxFiles;
    private EditText editTextMax, editTextDiretory, editTextDiretoryName;
    private ListView listView;
    
    private PickerPreferences.Builder builder;
    
     
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        checkBoxIcon = findViewById(R.id.activity_main_checkicon);
        checkBoxFiles = findViewById(R.id.activity_main_checkfile);
        editTextMax = findViewById(R.id.activity_main_editmax);
        editTextDiretory = findViewById(R.id.activity_main_editdiretory);
        editTextDiretoryName = findViewById(R.id.activity_main_editdiretory_name);
        listView = findViewById(R.id.activity_main_listview);
        
        builder = new PickerPreferences.Builder();
             
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data.getStringArrayListExtra(ArchivesPicker.KEY_STRING_ARRAYLIST_EXTRA));
        listView.setAdapter(arrayAdapter);
        }else if(resultCode == RESULT_CANCELED){
            Toast.makeText(this, "Cancel", 0).show();
        }
        
    }
	
    
    
    
    public void onPickerStart(View view){
        builder.setEnableHiddenIcon(checkBoxIcon.isChecked());
        builder.setHiddenArchives(checkBoxFiles.isChecked());
        builder.setMaxSelectFiles(Integer.parseInt(editTextMax.getText().toString()));
        builder.setRootDiretory(new File(editTextDiretory.getText().toString()));
        builder.setRootDiretoryName(editTextDiretoryName.getText().toString());
        
        ArchivesPicker.newInstance(this, builder.build()).forResult();
        
        
    }
    
} 
