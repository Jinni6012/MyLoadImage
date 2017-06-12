package com.example.imageloadaerlibrary.config;

import android.content.Context;
import android.support.annotation.NonNull;

import com.bumptech.glide.MemoryCategory;

/**
 * author : daiwenbo
 * e-mail : daiwwenb@163.com
 * date   : 2017/6/12
 * description   : Gilde初始化内存缓存参数配置
 */

public class GlideFileMemoryConfig {
    /*图片加载渠道*/
    private String channel;
    /*缓存文件名*/
    private String fileName;
    private Context context;
    /*磁盘缓存大小*/
    private int cacheSizeInM;
    /*动态调整滑动时的内存占用您的应用程序的某些阶段*/
    private MemoryCategory memoryCategory;
    /*true 缓存保存到应用下 false保存到SDK中*/
    private boolean asImternalcd;

    public GlideFileMemoryConfig(Builder builder) {
        this.fileName=builder.fileName;
        this.context=builder.context;
        this.cacheSizeInM=builder.chacheSizeInM;
        this.memoryCategory=builder.memoryCategory;
        this.asImternalcd=builder.asImternalcd;
        this.channel=builder.channel;
    }

    public String getChannel() {
        return channel;
    }

    public String getFilename() {
        return fileName;
    }

    public Context getContext() {
        return context;
    }

    public int getCacheSizeInM() {
        return cacheSizeInM;
    }

    public MemoryCategory getMemoryCategory() {
        return memoryCategory;
    }

    public boolean isAsImternalcd() {
        return asImternalcd;
    }
    public void deploy(){
        GlobalConfig.init(this);
    }

    public static class Builder {
        /*图片加载渠道*/
        private String channel;
        /*缓存文件名*/
        private String fileName;
        private Context context;
        /*磁盘缓存大小*/
        private int chacheSizeInM;
        /*动态调整滑动时的内存占用您的应用程序的某些阶段*/
        private MemoryCategory memoryCategory;
        /*true 缓存保存到应用下 false保存到SDK中*/
        private boolean asImternalcd;

        public void init(Context context) {
            this.context = context;
            new GlideFileMemoryConfig(this).deploy();
        }
        public Builder channel(@NonNull String channel){
            this.channel=channel;
            return this;
        }
        public Builder fileName(@NonNull String fileName) {
            this.fileName = fileName;
            return this;
        }

        public Builder cacheSizeInM(int cacheSizeInM) {
            this.chacheSizeInM = cacheSizeInM;
            return this;
        }

        public Builder memoryCategory(@NonNull MemoryCategory memoryCategory) {
            this.memoryCategory = memoryCategory;
            return this;
        }

        public Builder asImternalcd(@NonNull boolean asImternalcd) {
            this.asImternalcd = asImternalcd;
            return this;
        }
    }
}
