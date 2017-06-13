package com.example.imageloadaerlibrary.loader;

import android.content.Context;
import android.view.View;

import com.bumptech.glide.MemoryCategory;
import com.example.imageloadaerlibrary.config.GlideFileMemoryConfig;
import com.example.imageloadaerlibrary.config.GlobalConfig;
import com.example.imageloadaerlibrary.config.SingleConfig;
import com.example.imageloadaerlibrary.utils.DownLoadImageService;


/**
 * author : daiwenbo
 * e-mail : daiwwenb@163.com
 * date   : 2017/6/10
 * description   :图片加载器
 */

public class ImageLoader {
    /*默认缓存大小*/
    private  static  int CACHE_IMAGE_SIZE=250;

    /**
     * 采用配置参数实现
     * @return
     */
    public static GlideFileMemoryConfig.Builder create(){
        return new GlideFileMemoryConfig.Builder();
    }

    /**
     * 采用默认实现
     * @param context
     */
    public static void  init(Context context,String channel){
        init(context,CACHE_IMAGE_SIZE,channel);
    }
    public static void init(Context context,int cacheSize,String channel){
        init(context,cacheSize, MemoryCategory.NORMAL,channel);
    }
    public static void init(Context context,int cacheSize,MemoryCategory memoryCategory,String channel){
        init(context,cacheSize, MemoryCategory.NORMAL,true,channel);
    }

    /**
     *
     * @param context
     * @param cacheSize Glide默认磁盘缓存最大容量
     * @param memoryCategory 调整缓存的大小
     * @param isInternalCD false 缓存存到外部|true 图片缓存存到应用内部
     * @param channel 加载图片框架 Glide
     */
    public static void init(Context context,int cacheSize,MemoryCategory memoryCategory,boolean isInternalCD,String channel){
        GlobalConfig.init(context,cacheSize,memoryCategory,isInternalCD,channel);
    }
    private static ILoader getActualLoader() {
        return GlobalConfig.getiLoader();
    }

    /**
     * 设置加载加载图片是的加载相关参数
     * @param context
     * @return
     */
    public static SingleConfig.ConfigBuilder with(Context context){
        return new SingleConfig.ConfigBuilder(context);
    }
    /**
     * 根据系统内存状态动态调整内存使用策略
     */
    public static void trimMemory(int level){
        getActualLoader().trimMemory(level);
    }

    /**
     * 清理所有缓存
     */
    public static void clearAllMemoryCaches(){
        getActualLoader().clearAllMemoryCaches();
    }

    /**
     * 停止请求
     */
    public static void pauseRequest(){
        getActualLoader().pause();
    }
    /**
     * 恢复请求
     */
    public static void resumeRequest(){
        getActualLoader().resume();
    }
    /**
     * 清理指定View的缓存
     */
    public static void clearMemoryCache(View view){
        getActualLoader().clearMemoryCache(view);
    }
    /**
     * 清理磁盘缓存
     */
    public static void clearDiskCache(){
        getActualLoader().clearDiskCache();
    }
    /**
     * 清理内存缓存
     */
    public static void clearMemory(){
        getActualLoader().clearMemory();
    }

    /**
     * 下载bitmap 并保存到磁盘
     * @param downLoadImageService
     */
    public static void saveBitmapIntoGallery(DownLoadImageService downLoadImageService){
        getActualLoader().saveImageIntoGallery(downLoadImageService);
    }

}
