package com.example.imageloadaerlibrary.loader;

import android.content.Context;
import android.view.View;

import com.bumptech.glide.MemoryCategory;
import com.example.imageloadaerlibrary.config.GlideFileMemoryConfig;
import com.example.imageloadaerlibrary.config.SingleConfig;
import com.example.imageloadaerlibrary.utils.DownLoadImageService;


/**
 * author : daiwenbo
 * e-mail : daiwwenb@163.com
 * date   : 2017/6/10
 * description   : xxxx描述
 */

public interface ILoader {
    /**
     * 初始化操作
     * @param context
     * @param chacheSizeInM
     * @param memoryCategory
     * @param isImternalcd
     */
    void init(Context context, int chacheSizeInM, MemoryCategory memoryCategory, boolean isImternalcd);

    /**
     * 请求配置参数
     * @param config
     */
    void request(SingleConfig config);

    /**
     * 停止请求
     */
    void pause();
    void resume();

    /**
     * 清理磁盘缓存
     */
    void clearDiskCache();

    /**
     * 清理内存缓存
     * @param view 指定view
     */
    void clearMemoryCache(View view);

    /**
     * 清理内存缓存
     */
    void clearMemory();

    /**
     * 判断是否有该url的缓存
     * @param url
     * @return
     */
    boolean isCached(String url);
    void trimMemory(int level);

    /**
     * 清理所有的缓存
     */
    void clearAllMemoryCaches();

    /**
     * 下载图片
     * @param downLoadImageService
     */
    void saveImageIntoGallery(DownLoadImageService downLoadImageService);

    /**
     * 配置内存缓存相关参数
     * @param glideFileMemoryConfig
     */
    void deploy(GlideFileMemoryConfig glideFileMemoryConfig);
}
