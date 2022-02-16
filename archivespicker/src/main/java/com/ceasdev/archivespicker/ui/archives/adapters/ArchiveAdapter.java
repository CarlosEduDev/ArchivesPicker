package com.ceasdev.archivespicker.ui.archives.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.ceasdev.archivespicker.R;
import com.ceasdev.archivespicker.data.FileType;
import com.ceasdev.archivespicker.data.models.Archive;
import com.ceasdev.archivespicker.data.works.IconApkWork;
import com.ceasdev.archivespicker.data.works.ImageWork;
import com.ceasdev.archivespicker.data.works.abstraction.OnViewBehaviorControl;
import com.ceasdev.archivespicker.utils.ResourceUtil;
import java.util.ArrayList;
import java.util.List;
import com.ceasdev.archivespicker.utils.FileUtil;

public class ArchiveAdapter extends Adapter<ArchiveAdapter.ArchiveViewHolder>{

    public interface OnArchiveListener{
        void onArchiveClick(Archive archive, int position);
        void onArchiveIconClick(Archive archive, int position);
        void onArchiveSelected(List<String> listSelected);
    }

    private final OnArchiveListener onArchiveListener;
    private List<Archive> listArchive;
    private ArrayList<String> listFilesSelected;

    public ArchiveAdapter(OnArchiveListener onArchiveListener){
        this.onArchiveListener = onArchiveListener;
        this.listArchive = new ArrayList<>();
        this.listFilesSelected = new ArrayList<>();
    }

    @Override
    public ArchiveAdapter.ArchiveViewHolder onCreateViewHolder(ViewGroup viewGroup, int type){
        return new ArchiveViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_archive, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(ArchiveAdapter.ArchiveViewHolder archiveViewHolder, int position){
        archiveViewHolder.bindView(listArchive.get(position), position);
    }

    @Override
    public int getItemCount(){
        return listArchive.size();
    }


    public void notifyList(List<Archive> listArchive){
        if(this.listArchive == null) this.listArchive = new ArrayList<>();
        this.listArchive.clear();
        this.listArchive.addAll(listArchive);  
        updateItemSelected();
        notifyDataSetChanged();
    }



    public void selectItem(int position){
        if(listFilesSelected == null) listFilesSelected = new ArrayList<>();
        Archive archive = listArchive.get(position);
        archive.setSelected(true);
        listFilesSelected.add(archive.getFile().getAbsolutePath());
        onArchiveListener.onArchiveSelected(listFilesSelected);
        notifyItemChanged(position);
    }


    public void unSelectItem(int position){
        if(listFilesSelected == null) return;
        Archive archive = listArchive.get(position);
        archive.setSelected(false);
        listFilesSelected.remove(archive.getFile().getAbsolutePath());
        onArchiveListener.onArchiveSelected(listFilesSelected);
        notifyItemChanged(position);
    }


    public boolean isSelected(int position){
        return listArchive.get(position).isSelected();
    }
    
    
    public void unSelectAll(){
        if(listFilesSelected == null) return;
        listFilesSelected.clear();
        for(Archive archive : listArchive)
        if(archive.isSelected()) archive.setSelected(false);
        onArchiveListener.onArchiveSelected(listFilesSelected);
        notifyDataSetChanged(); 
    }


    public void animateIconPosition(int position){
        if(listArchive == null || getItemCount() <= 1) return;
        listArchive.get(position).setAnimateThis(true);
        notifyItemChanged(position);
    }

    public int getCountSelectedFiles(){
        if(listFilesSelected==null) return 0;
        return listFilesSelected.size();
    }
    
    
    public ArrayList<String> getListFilesSelected(){
        return listFilesSelected;
    }
    

    private void updateItemSelected(){
        if(listFilesSelected == null || listFilesSelected.size() <= 0) return;
        for(String fileSelect : listFilesSelected)
            for(int i = 0; i < listArchive.size(); i++){
                Archive archive = listArchive.get(i);
                if(archive.getFile().getAbsolutePath().equals(fileSelect)) archive.setSelected(true);
            }
    }


    public class ArchiveViewHolder extends ViewHolder implements OnClickListener, OnViewBehaviorControl<ImageView>{


        private ArchiveViewHolderBinding binding;
        private Context context;

        private Drawable
        icon_folder,
        icon_file,
        icon_file_image,
        icon_file_document,
        icon_file_music,
        icon_file_video,
        icon_file_apk;
        private int dpi_icon;
        private Animation anim_fade_in, anim_slide_bottom_in;

        public ArchiveViewHolder(View itemView){
            super(itemView);
            init();
            initResouces();
            setupListener();
        }


        @Override
        public void onClick(View view){

            final int viewId = view.getId();
            final Archive archive = (Archive) binding.rootView.getTag(R.string.TAG_ARCHIVE);
            final int position = (Integer) binding.rootView.getTag(R.string.TAG_POSITION);

            if(archive == null) return;

            if(viewId == binding.rootView.getId())
                onArchiveListener.onArchiveClick(archive, position);
            else if(viewId == binding.cardView.getId())
                onArchiveListener.onArchiveIconClick(archive, position);
            else
                return;

        }



        @Override
        public ImageView onViewEmptyBehavior(ImageView imageView){
            imageView.startAnimation(anim_fade_in);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setPadding(dpi_icon, dpi_icon, dpi_icon, dpi_icon);
            return imageView;
        }

        @Override
        public ImageView onViewFullBehavior(ImageView imageView){
            imageView.startAnimation(anim_fade_in);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(0, 0, 0, 0);
            return imageView;
        }



        private void init(){
            binding = new ArchiveViewHolderBinding(itemView);
            context = itemView.getContext();
            binding.rootView.setBackground(ResourceUtil.getDrawable(context, R.drawable.ripple));
        }


        private void initResouces(){
            this.icon_folder = ResourceUtil.getDrawable(context, R.drawable.ic_folder);
            this.icon_file = ResourceUtil.getDrawable(context, R.drawable.ic_file);
            this.icon_file_image = ResourceUtil.getDrawable(context, R.drawable.ic_file_image);
            this.icon_file_document = ResourceUtil.getDrawable(context, R.drawable.ic_file_document);
            this.icon_file_music = ResourceUtil.getDrawable(context, R.drawable.ic_file_music);
            this.icon_file_video = ResourceUtil.getDrawable(context, R.drawable.ic_file_video);
            this.icon_file_apk = ResourceUtil.getDrawable(context, R.drawable.ic_android_debug_bridge);
            this.dpi_icon = ResourceUtil.getDip(context, 10);
            this.anim_fade_in = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
            this.anim_slide_bottom_in = AnimationUtils.loadAnimation(context, R.anim.design_bottom_sheet_slide_in);
        }


        private void setupListener(){
            binding.rootView.setOnClickListener(this);
            binding.cardView.setOnClickListener(this);
        }


        private void setTitle(String name){
            binding.title.setText(name);
        }


        private void setSubtitle(Archive archive){
            binding.subtitle.setText((archive.isFile() ? archive.getSizeString() + " | " : "") + archive.getLastModifiedString());
        }


        public void setIcon(Archive archive){
            final FileType fileType = archive.getFileType();

            if(archive.getFile().isHidden()){
                binding.icon.setAlpha(0.5f); 
            }else{
                binding.icon.setAlpha(1.0f);
            }

            if(fileType != FileType.APK || fileType != FileType.IMAGE){
                binding.icon.setScaleType(ImageView.ScaleType.FIT_CENTER);
                binding.icon.setPadding(dpi_icon, dpi_icon, dpi_icon, dpi_icon);
            }

            if(archive.isDirectory())
                binding.icon.setImageDrawable(icon_folder);
            else if(fileType == FileType.TEXT)
                binding.icon.setImageDrawable(icon_file_document);
            else if(fileType == FileType.AUDIO)
                binding.icon.setImageDrawable(icon_file_music);
            else if(fileType == FileType.VIDEO)
                binding.icon.setImageDrawable(icon_file_video);
            else if(fileType == FileType.UNKNOWN)
                binding.icon.setImageDrawable(icon_file);
            else if(fileType == FileType.IMAGE)
                ImageWork.newInstance(this, binding.icon).holder(icon_file_image).execute(archive.getFile());
            else if(fileType == FileType.APK)
                IconApkWork.newInstance(this, binding.icon).holder(icon_file_apk).execute(archive.getFile());
            else
                binding.icon.setImageDrawable(icon_file);
        }


        private void setIconRight(Archive archive){
            if(archive.isDirectory()){
                binding.icon_right.setVisibility(View.VISIBLE);
                binding.icon_right.setImageResource(R.drawable.ic_chevron_right);
            }else{
                binding.icon_right.setImageResource(R.drawable.ic_check);
                if(archive.isSelected())
                    binding.icon_right.setVisibility(View.VISIBLE);
                else
                    binding.icon_right.setVisibility(View.GONE);
            }
        }


        private void animationFolder(Archive archive){
            if(archive.isFile()) return;
            anim_slide_bottom_in.setDuration(300);
            anim_slide_bottom_in.setStartOffset(200);
            anim_slide_bottom_in.setInterpolator(AnimationUtils.loadInterpolator(context, android.R.anim.accelerate_decelerate_interpolator));
            if(archive.isAnimateThis()){
                binding.icon.startAnimation(anim_slide_bottom_in);
                archive.setAnimateThis(false);
            }
        }



        public void bindView(Archive archive, int position){
            binding.rootView.setTag(R.string.TAG_ARCHIVE, archive);
            binding.rootView.setTag(R.string.TAG_POSITION, position);
            setTitle(archive.getName());
            setSubtitle(archive);
            setIcon(archive);
            setIconRight(archive);
            animationFolder(archive);
            if(archive.isSelected())
                binding.rootView.setBackgroundResource(R.color.ceasArchivesColorControlHighlight);
            else
                binding.rootView.setBackgroundResource(R.color.ceasArchivesColorBackground);
            

        }

    }


}
