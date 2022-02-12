package com.ceasdev.archivespicker.ui.archives;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.ceasdev.archivespicker.R;
import com.ceasdev.archivespicker.ui.base.BaseViewBinding;
import com.google.android.material.button.MaterialButton;

public class ArchivesPickerViewBinding extends BaseViewBinding {

    public Toolbar toolbar;
    public RecyclerView recyclerDirectory, recyclerArchive;
    public SwipeRefreshLayout swipeRefresh;
    public View viewNoItem;
    public ProgressBar progressBar;
    public MaterialButton buttonSelect;
    
    public ArchivesPickerViewBinding(LayoutInflater layoutInflater){
        super(layoutInflater, R.layout.activity_archives_picker);
    }
    
    @Override
    protected void initViews(){
        toolbar = find(R.id.activity_archives_picker_toolbar);
        recyclerDirectory = find(R.id.activity_archives_picker_recyclerview_paths);
        recyclerArchive = find(R.id.activity_archives_picker_recyclerview);
        swipeRefresh = find(R.id.activity_archives_picker_swipe_refresh);
        viewNoItem = find(R.id.activity_archives_picker_no_item);
        progressBar = find(R.id.activity_archives_picker_progressbar);
        buttonSelect = find(R.id.activity_archives_picker_select);
    }

    
    
    
}
