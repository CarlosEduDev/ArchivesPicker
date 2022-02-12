package com.ceasdev.archivespicker.data.works;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import com.ceasdev.archivespicker.data.works.abstraction.OnViewBehaviorControl;
import com.ceasdev.archivespicker.data.works.abstraction.SimpleWork;
import java.io.File;

public class IconApkWork extends SimpleWork<File, Void, Drawable>{

    private OnViewBehaviorControl<ImageView> onViewBehaviorControl;
    private ImageView imageView;
    private Drawable holderDrawable;

    private IconApkWork(OnViewBehaviorControl<ImageView> onViewBehaviorControl, ImageView imageView){
        this.onViewBehaviorControl = onViewBehaviorControl;
        this.imageView = imageView;
    }

    public static IconApkWork newInstance(ImageView imageView){
        return new IconApkWork(null, imageView);
    }

    public static IconApkWork newInstance(OnViewBehaviorControl<ImageView> onViewBehaviorListener, ImageView imageView){
        return new IconApkWork(onViewBehaviorListener, imageView);
    }


    public IconApkWork holder(Drawable holderDrawable){
        this.holderDrawable = holderDrawable;
        return this;
    }


    @Override
    protected void onPrepareWork(){
        checkEmptyImageBehavior();
        if(holderDrawable != null)
            imageView.setImageDrawable(holderDrawable);
    }


    @Override
    protected Drawable doWorkInBackground(File[] params) throws Exception{
        PackageManager packageManager = imageView.getContext().getPackageManager();
        PackageInfo packageInfo = packageManager.getPackageArchiveInfo(params [0].getAbsolutePath(), PackageManager.GET_ACTIVITIES);
        packageInfo.applicationInfo.sourceDir = params [0].getAbsolutePath();
        packageInfo.applicationInfo.publicSourceDir = params [0].getAbsolutePath();
        return packageInfo.applicationInfo.loadIcon(packageManager);
    }

    @Override
    protected void onResultWork(Drawable result){
        checkFullImageBehavior();
        if(result != null)
            imageView.setImageDrawable(result);
        else onFailureWork(new Exception());
    }

    @Override
    protected void onFailureWork(Exception exception){
        checkEmptyImageBehavior();
        if(holderDrawable != null)
            imageView.setImageDrawable(holderDrawable);
    }


    private void checkEmptyImageBehavior(){
        if(onViewBehaviorControl != null){
            ImageView image = onViewBehaviorControl.onViewEmptyBehavior(imageView);
            if(image != null) imageView = image;
        }
    }


    private void checkFullImageBehavior(){
        if(onViewBehaviorControl != null){
            ImageView image = onViewBehaviorControl.onViewFullBehavior(imageView);
            if(image != null) imageView = image;
        }
    }

}
