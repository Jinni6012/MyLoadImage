package com.example.imageloadaerlibrary.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * author : daiwenbo
 * e-mail : daiwwenb@163.com
 * date   : 2017/6/12
 * description   : 文件操作类
 */

public class FileUtils {
    /**
     * 创建目标文件
     * @param uniqueName
     * @return
     */
    public static File getDiskCacheDir(Context context,String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getAbsolutePath();
        } else {
            cachePath = context.getCacheDir().getAbsolutePath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }
}
