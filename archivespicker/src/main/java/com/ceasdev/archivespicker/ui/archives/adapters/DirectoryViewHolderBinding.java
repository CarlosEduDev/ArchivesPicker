package com.ceasdev.archivespicker.ui.archives.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.ceasdev.archivespicker.R;
import com.ceasdev.archivespicker.ui.base.BaseViewBinding;

public class DirectoryViewHolderBinding extends BaseViewBinding{

    public TextView title;
    public ImageView icon, icon_root;

    public DirectoryViewHolderBinding(View view){
        super(view);
    }

    @Override
    protected void initViews(){
        title = find(R.id.row_directory_title);
        icon = find(R.id.row_directory_icon);
        icon_root = find(R.id.row_directory_icon_root);
    }


}
