package com.ceasdev.archivespicker.ui.archives.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.ceasdev.archivespicker.R;
import com.ceasdev.archivespicker.ui.base.BaseViewBinding;
import com.google.android.material.card.MaterialCardView;

public class ArchiveViewHolderBinding extends BaseViewBinding {

    public ImageView icon, icon_right;
    public TextView title, subtitle;
    public MaterialCardView cardView;

    public ArchiveViewHolderBinding(View itemView){
        super(itemView);
    }

    @Override
    protected void initViews(){
        icon = find(R.id.row_archive_imageview_icon);
        icon_right = find(R.id.row_archive_icon_right);
        title = find(R.id.row_archive_textview_title);
        subtitle = find(R.id.row_archive_textview_subtitle);
        cardView = find(R.id.row_archive_cardview);
    }

}
