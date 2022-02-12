package com.ceasdev.archivespicker.data.works;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import com.ceasdev.archivespicker.data.works.abstraction.OnViewBehaviorControl;
import com.ceasdev.archivespicker.data.works.abstraction.SimpleWork;
import com.ceasdev.archivespicker.utils.FileUtil;
import java.io.File;

public class ImageWork extends SimpleWork<File, Void, Bitmap>{

    private OnViewBehaviorControl<ImageView> onViewBehaviorControl;
    private ImageView imageView;
    private Drawable holderDrawable;

    private ImageWork(OnViewBehaviorControl<ImageView> onViewBehaviorControl, ImageView imageView){
        this.onViewBehaviorControl = onViewBehaviorControl;
        this.imageView = imageView;
    }
    
    public static ImageWork newInstance(ImageView imageView){
        return new ImageWork(null, imageView);
    }

    public static ImageWork newInstance(OnViewBehaviorControl<ImageView> onViewBehaviorListener, ImageView imageView){
        return new ImageWork(onViewBehaviorListener, imageView);
    }


    public ImageWork holder(Drawable holderDrawable){
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
    protected Bitmap doWorkInBackground(File[] params) throws Exception{
        return FileUtil.DecodeBitmap(params [0], 128, 128);
    }

    @Override
    protected void onResultWork(Bitmap result){
        try{
            if(result == null) throw new Exception("Bitmap is null");
            Bitmap BitmapBlank = Bitmap.createBitmap(result.getWidth(), result.getHeight(), result.getConfig());
            if(result.sameAs(BitmapBlank)) throw new Exception("Bitmap is empty");
            checkFullImageBehavior();
            if(imageView != null)
                imageView.setImageBitmap(result);
        }catch(Exception e){
            onFailureWork(e);
        }

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
