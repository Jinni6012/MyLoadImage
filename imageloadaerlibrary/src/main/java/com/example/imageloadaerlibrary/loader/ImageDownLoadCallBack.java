package com.example.imageloadaerlibrary.loader;

import android.graphics.Bitmap;

/**
 * author : daiwenbo
 * e-mail : daiwwenb@163.com
 * date   : 2017/6/12
 * description   : bitmap 下载回调接口
 */

public interface ImageDownLoadCallBack {
    void onDownLoadSuccess(Bitmap bitmap);
    void onDownLoadFailed();
}
