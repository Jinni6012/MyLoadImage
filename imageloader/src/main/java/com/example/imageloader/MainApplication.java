package com.example.imageloader;

import android.app.Application;
import android.content.Context;

import com.bumptech.glide.MemoryCategory;
import com.example.imageloadaerlibrary.config.ChannelConfig;
import com.example.imageloadaerlibrary.loader.ImageLoader;


/**
 * author : daiwenbo
 * e-mail : daiwwenb@163.com
 * date   : 2017/6/12
 * description   : xxxx描述
 */

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoader.create()
                .fileName("image_cache")//自定义图片缓存目录
                .channel(ChannelConfig.GLIDE)//设置图片加载的渠道框架
                .cacheSizeInM(100)//分配缓存空间 100*1024*1024
                .asImternalcd(true)// true 目录/ false sd卡
                .memoryCategory(MemoryCategory.HIGH)//配置占用个内存空间
                .init(this);
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        ImageLoader.trimMemory(level);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        ImageLoader.clearMemory();
    }
}
