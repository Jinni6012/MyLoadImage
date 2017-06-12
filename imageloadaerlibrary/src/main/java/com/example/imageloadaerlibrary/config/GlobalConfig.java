package com.example.imageloadaerlibrary.config;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.bumptech.glide.MemoryCategory;
import com.example.imageloadaerlibrary.loader.GlideLoader;
import com.example.imageloadaerlibrary.loader.ILoader;


/**
 * author : daiwenbo
 * e-mail : daiwwenb@163.com
 * date   : 2017/6/10
 * description   : xxxx描述
 */

public class GlobalConfig {
    public static Context context;
    private static ILoader I_LOADER;
    /**
     * https是否忽略校验,默认不忽略
     */
    public static boolean ignoreCertificateVerify = false;
    public static void init(Context con, int cacheSize, MemoryCategory memoryCategory, boolean isInternalCD,@NonNull String channel) {
        context=con;
        //初始化Gilde
        getLoader(channel).init(con, cacheSize, memoryCategory, isInternalCD);
    }
    public static void init( GlideFileMemoryConfig glideFileMemoryConfig){
        context=glideFileMemoryConfig.getContext();
        //初始化Gilde
        getLoader(glideFileMemoryConfig.getChannel()).deploy(glideFileMemoryConfig);
    }

    /**
     * 返回图片加载渠道
     * @param channel
     * @return
     */
    public static ILoader getLoader(String channel) {
        ILoader iLoader = null;
        switch (channel){
            case ChannelConfig.GLIDE:
                iLoader=new GlideLoader();
                break;
        }
        I_LOADER=iLoader;
        return iLoader;
    }

    public static ILoader getiLoader() {
        return I_LOADER;
    }

    /**
     * 创建handler 主线程中
     */
    private static Handler mainHandler;
    public static Handler getMainHandler(){
        if(mainHandler==null){
            mainHandler=new Handler(Looper.getMainLooper());
        }
        return mainHandler;
    }

}
