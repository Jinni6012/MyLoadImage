package com.example.imageloadaerlibrary.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.imageloadaerlibrary.loader.ImageDownLoadCallBack;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * author : daiwenbo
 * e-mail : daiwwenb@163.com
 * date   : 2017/6/10
 * description   : 下载图片工具类
 */

public class DownLoadImageService implements Runnable {
    private Context context;
    private String url;
    private String fileName;
    private ImageDownLoadCallBack callback;
    private File currentFile;


    public DownLoadImageService(Context context, String url, String fileName, ImageDownLoadCallBack callback) {
        this.context = context;
        this.url = url;
        this.fileName = fileName;
        this.callback = callback;
    }



    @Override
    public void run() {
        Bitmap bitmap=null;
        try {
            bitmap= Glide.with(context)
                    .load(url)
                    .asBitmap()
                    .into(Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL)//下载bitmap的宽高设置
                    .get();
            if(bitmap!=null){
                saveImageToGallery(context,bitmap);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }finally {
            if(null!=bitmap&&currentFile.exists()){
                //回调成功
                callback.onDownLoadSuccess(bitmap);
            }else {
                //回调失败
                callback.onDownLoadFailed();
            }

        }
    }

    /**
     * 保存图片到本地
     * @param context
     * @param bitmap
     */
    private void saveImageToGallery(Context context, Bitmap bitmap) {
        FileOutputStream fileOutputStream=null;
        try {
            currentFile =FileUtils.getDiskCacheDir(context.getApplicationContext(),fileName);
            currentFile.getParentFile().mkdir();
            currentFile.createNewFile();
            fileOutputStream= new FileOutputStream(currentFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
            fileOutputStream.flush();
        } catch (IOException e) {
                e.printStackTrace();
               Log.e("GlideLoader","保存图片失败");
        }finally {
            if (fileOutputStream!=null){
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
