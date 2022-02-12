package com.ceasdev.archivespicker.ui.archives.adapters;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import android.view.ViewGroup;
import com.ceasdev.archivespicker.data.models.Directory;
import java.util.List;
import java.util.ArrayList;
import android.content.Context;
import com.ceasdev.archivespicker.utils.ResourceUtil;
import com.ceasdev.archivespicker.R;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;

public class DirectoryAdapter extends Adapter<DirectoryAdapter.DirectoryViewHolder>{

    public interface OnDirectoryListener{
        void onClickDirectory(Directory directory);
    }

    private OnDirectoryListener onDirectoryListener;
    private List<Directory> listDirectory;

    public DirectoryAdapter(OnDirectoryListener onDirectoryListener){
        this.onDirectoryListener = onDirectoryListener;
        this.listDirectory = new ArrayList<>();
    }



    @Override
    public DirectoryAdapter.DirectoryViewHolder onCreateViewHolder(ViewGroup viewGroup, int flag){
        return new DirectoryViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_directory, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(DirectoryAdapter.DirectoryViewHolder holder, int position){
        holder.bindView(listDirectory.get(position), position);
    }

    @Override
    public int getItemCount(){
        return listDirectory.size();
    }


    public void notifyList(List<Directory> listDirectory){
        if(this.listDirectory == null) this.listDirectory = new ArrayList<>();
        this.listDirectory.clear();
        this.listDirectory.addAll(listDirectory);
        notifyDataSetChanged();
    }



    public class DirectoryViewHolder extends ViewHolder implements OnClickListener{


        private DirectoryViewHolderBinding binding;
        private Context context;


        private int colorTitle, colorSubtitle;
        private static final float alphaEnabled = 1f, alphaDisabled = 0.5f;

        public DirectoryViewHolder(View itemView){
            super(itemView);
            init();
            initResources();
        }
        
        
        @Override
        public void onClick(View view){
            final Directory directory = (Directory) binding.rootView.getTag();
            if(onDirectoryListener==null || directory == null) return;
            onDirectoryListener.onClickDirectory(directory);
        }
        

        private void init(){
            binding = new DirectoryViewHolderBinding(itemView);
            context = itemView.getContext();
            binding.rootView.setBackground(ResourceUtil.getDrawable(context, R.drawable.ripple));
            binding.rootView.setOnClickListener(this);
        }

        private void initResources(){
            colorTitle = ResourceUtil.getColor(context, R.color.ceasArchivesColorTitle);
            colorSubtitle = ResourceUtil.getColor(context, R.color.ceasArchivesColorSubtitle);
        }


        public void bindView(Directory directory, int position){
            binding.rootView.setTag(directory);

            binding.title.setText(directory.getName());
            binding.icon_root.setVisibility(View.GONE);
            binding.icon_root.setAlpha(alphaDisabled);
            binding.title.setVisibility(View.VISIBLE);
            binding.title.setTextColor(colorSubtitle);
            binding.icon.setVisibility(View.VISIBLE);

            if(position == 0){
                binding.icon_root.setVisibility(View.VISIBLE);
                if(getItemCount() == 1){
                    binding.icon_root.setAlpha(alphaEnabled);
                    binding.title.setTextColor(colorTitle);
                    binding.icon.setVisibility(View.GONE);
                }
            }else if(position == getItemCount() - 1){
                binding.icon.setVisibility(View.GONE);
                binding.title.setTextColor(colorTitle);
            }
            
        }


    }

}
