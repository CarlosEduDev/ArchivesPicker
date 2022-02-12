package com.ceasdev.archivespicker.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.webkit.MimeTypeMap;
import java.io.File;
import java.text.DecimalFormat;

public class FileUtil {
    
    
    public static File getRootDiretory(){
        return Environment.getExternalStorageDirectory();
    }
    
    
    public static String getExtension(File file){
        String name = file.getName();
        int index = name.lastIndexOf('.');
        if(file.isDirectory() || index == -1) return null;
        return name.substring(index);
    }

    public static String getMimeType(File file){
        String extension = getExtension(file);
        if(extension == null || extension.isEmpty()) return null;
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension.substring(1).toLowerCase());
    }


    public static String getSizeFormated(File file){
        final long size = file.length();
        return getSizeFormated(size);
    }


    public static String getSizeFormated(long size){
        if(size <= 0) return "0B";
        String result = null;
        final String[] units = new String[] { "B", "KB", "MB", "GB", "TB", "PB", "EB" };
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        result = new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units [digitGroups];
        return result;
    }
    
    
    public static Bitmap DecodeBitmap(File file, int width, int height){
        final BitmapFactory.Options options = new BitmapFactory.Options();
        final String path = file.getAbsolutePath();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        options.inSampleSize = calculateInSampleSize(options, width, height);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }


    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight){
        final int width = options.outWidth;
        final int height = options.outHeight;
        int inSampleSize = 1;

        if(height > reqHeight || width > reqWidth){
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            while((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth)
                inSampleSize *= 2;
        }
        return inSampleSize;
    }
    
    
}
