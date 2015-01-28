package com.example.telstranewsfeed;

import java.io.File;
import android.content.Context;

/**
 * This class is used to store the images into file
 */
public class FileCache {
    /** File to store images*/
    private File cacheDir;

    /**
     * Used to check and save the image into file.
     */
    public FileCache(Context context){
        //Find the dir to save cached images
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
            cacheDir=new File(android.os.Environment.getExternalStorageDirectory(),"LazyList");
        else
            cacheDir=context.getCacheDir();
        if(!cacheDir.exists())
            cacheDir.mkdirs();
    }

    /**
     * get the file from location.
     * @param url
     * @return File
     */
    public File getFile(String url){
       
        String filename=String.valueOf(url.hashCode());
        File f = new File(cacheDir, filename);
        return f;

    }

    /**
     * Clean up the files.
     */
    public void clear(){
        File[] files=cacheDir.listFiles();
        if(files==null)
            return;
        for(File f:files)
            f.delete();
    }

}