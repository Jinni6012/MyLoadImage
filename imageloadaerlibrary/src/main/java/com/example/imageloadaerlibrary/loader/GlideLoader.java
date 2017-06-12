package com.example.imageloadaerlibrary.loader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.MemoryCategory;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.imageloadaerlibrary.config.AnimationMode;
import com.example.imageloadaerlibrary.config.GlideFileMemoryConfig;
import com.example.imageloadaerlibrary.config.GlobalConfig;
import com.example.imageloadaerlibrary.config.PriorityMode;
import com.example.imageloadaerlibrary.config.ScaleMode;
import com.example.imageloadaerlibrary.config.ShapeMode;
import com.example.imageloadaerlibrary.config.SingleConfig;
import com.example.imageloadaerlibrary.utils.DownLoadImageService;
import com.example.imageloadaerlibrary.utils.ImageUtil;


import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.ColorFilterTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.CropSquareTransformation;
import jp.wasabeef.glide.transformations.GrayscaleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import jp.wasabeef.glide.transformations.gpu.BrightnessFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.ContrastFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.InvertFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.PixelationFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.SepiaFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.SketchFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.SwirlFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.ToonFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.VignetteFilterTransformation;

import static com.example.imageloadaerlibrary.config.GlobalConfig.context;


/**
 * author : daiwenbo
 * e-mail : daiwwenb@163.com
 * date   : 2017/6/10
 * description   : Glide图片加载
 */

public class GlideLoader implements ILoader {
    /**
     * 初始化Gilde 内存缓存配置
     * @param context
     * @param chacheSizeInM 占用应用内存的大小 M
     * @param memoryCategory 动态调整滑动时的内存占用您的应用程序的某些阶段
     * @param isImternalcd true 存储在应用目录下 false存储在sd卡上
     *
     */
    @Override
    public void init(Context context, int chacheSizeInM, MemoryCategory memoryCategory, boolean isImternalcd) {
        Glide.get(context).setMemoryCategory(memoryCategory);//动态设置在应用中的缓存大小
        GlideBuilder builder=new GlideBuilder(context);
        //设置缓存的存放目录 应用目录下/sd卡上
        if(isImternalcd){
            builder.setDiskCache(new InternalCacheDiskCacheFactory(context,chacheSizeInM*1024*1024));
        }else {
            builder.setDiskCache(new ExternalCacheDiskCacheFactory(context,chacheSizeInM*1024*1024));
        }
    }

    /**
     * 通过参数配置缓存大小设置
     * @param glideFileMemoryConfig
     */
    @Override
    public void deploy(GlideFileMemoryConfig glideFileMemoryConfig) {
        Context context = glideFileMemoryConfig.getContext();
        Glide.get(context).setMemoryCategory(glideFileMemoryConfig.getMemoryCategory());//动态设置在应用中的缓存大小
        GlideBuilder builder=new GlideBuilder(context);

        boolean asImternalcd = glideFileMemoryConfig.isAsImternalcd();//是否使用SD卡
        String filename = glideFileMemoryConfig.getFilename();//自定义缓存文件名
        int cacheSizeInM = glideFileMemoryConfig.getCacheSizeInM();//缓存空间
        if(asImternalcd){
            builder.setDiskCache(new InternalCacheDiskCacheFactory(context,filename,cacheSizeInM*1024*1024));
        }else {
            builder.setDiskCache(new ExternalCacheDiskCacheFactory(context,filename,cacheSizeInM*1024*1024));
        }

    }

    /**
     *
     * @param config
     */
    @Override
    public void request(final SingleConfig config) {
        RequestManager requestManager=Glide.with(config.getContext());

        DrawableTypeRequest drawableTypeRequest= getDrawableTypeRequest(config,requestManager);

        if(drawableTypeRequest==null){
            Log.e("imageLoader","drawableTypeRequest is null");
            return;
        }
        //设置磁盘缓存策略 源码默认  private DiskCacheStrategy diskCacheStrategy = DiskCacheStrategy.RESULT; 仅仅缓存最终的图像
        if(config.getDiskCacheStrategy()!=null){
            drawableTypeRequest.diskCacheStrategy(config.getDiskCacheStrategy());
        }
        //下载bitmap
        if (config.isAsBitmap()){
            SimpleTarget target=new SimpleTarget<Bitmap>(config.getWidth(),config.getHeight()){
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    config.getBitmapListener().onSuccess(resource);
                }
            };
            setShapeModeAndBlur(config,drawableTypeRequest);

            //请求图片
            drawableTypeRequest.asBitmap().into(target);
        }else {
            //设置占为符
            if(shouldSetPlaceHolder(config)){
                drawableTypeRequest.placeholder(config.getPlaceHolderResId());
            }
            int scaleMode = config.getScaleMode();
            //拉伸模式设置
            switch (scaleMode){
                case ScaleMode.CENTER_CROP:
                    drawableTypeRequest.centerCrop();
                    break;
                case ScaleMode.FIT_CENTER:
                    drawableTypeRequest.fitCenter();
                    break;
                default:
                    drawableTypeRequest.fitCenter();
                    break;
            }
            //其他特效设置
            setShapeModeAndBlur(config,drawableTypeRequest);
            /**
             * 如果你传递一个0.1f作为参数，Glide会加载原始图片大小的10%的图片。如果原始图片有1000x1000像素，
             * 缩略图的分辨率为100x100像素。由于图片将会比ImageView小，你需要确保缩放类型是否正确
             *
             */
            //设置缩略图
            if(config.getThumbnail()!=0){
                drawableTypeRequest.thumbnail(config.getThumbnail());
            }
            //设置图片加载的分辨率
            if(config.getoWidth()!=0&&config.getoWidth()!=0){
                drawableTypeRequest.override(config.getoWidth(),config.getoHeight());
            }

            //设置加载动画
            setAnimator(config,drawableTypeRequest);
            //设置图片加载优先级
            setPriority(config,drawableTypeRequest);
            //设置加载失败的位图
            if(config.getErrorResId()>0){
                drawableTypeRequest.error(config.getErrorResId());
            }
            //是否加载gif
            if(config.isGif()){
                drawableTypeRequest.asGif();
            }
            //图加载到ImageView上面
            if(config.getTarget() instanceof ImageView){
                drawableTypeRequest.into((ImageView)config.getTarget());
            }
        }
    }

    /**
     * 设置图片加载的优先级
     * @param config
     * @param drawableTypeRequest
     */
    private void setPriority(SingleConfig config, DrawableTypeRequest drawableTypeRequest) {
        switch (config.getPriority()){
            case PriorityMode.PRIORITY_LOW://后加载
                drawableTypeRequest.priority(Priority.LOW);
                break;
            case PriorityMode.PRIORITY_NORMAL://默认
                drawableTypeRequest.priority(Priority.NORMAL);
                break;
            case PriorityMode.PRIORITY_HIGH://先加载
                drawableTypeRequest.priority(Priority.HIGH);
                break;
            case PriorityMode.PRIORITY_IMMEDIATE://立即
                drawableTypeRequest.priority(Priority.IMMEDIATE);
                break;
            default:
                drawableTypeRequest.priority(Priority.IMMEDIATE);
                break;
        }

    }

    /**
     * 设置加载动画
     * @param config
     * @param drawableTypeRequest
     */
    private void setAnimator(SingleConfig config, DrawableTypeRequest drawableTypeRequest) {
            switch (config.getAnimationType()){
                case AnimationMode.ANIMATIONID: //R.anim.....
                    drawableTypeRequest.animate(config.getAnimationId());
                    break;
                case AnimationMode.ANIMATOR://兼容3.0以下动画
                    drawableTypeRequest.animate(config.getAnimator());
                    break;
                case AnimationMode.ANIMATION://Animation
                    drawableTypeRequest.animate(config.getAnimation());
                    break;
                default:
                    break;
            }

    }

    /**
     * 设置图片滤镜和形状
     * @param config
     * @param drawableTypeRequest
     */
    private void setShapeModeAndBlur(SingleConfig config, DrawableTypeRequest drawableTypeRequest) {
        int count = 0;
        //获取是否有特效处理 没有就直接结束方法
        int transSize = statisticsCount(config);
        if(transSize==0){
            return;
        }
        //样式集合初始化
        Transformation[] transformation = new Transformation[transSize];
        //模糊
        if (config.isNeedBlur()) {
            transformation[count] = new BlurTransformation(config.getContext(), config.getBlurRadius());
            count++;
        }
        //亮度
        if (config.isNeedBrightness()) {
            transformation[count] = new BrightnessFilterTransformation(config.getContext(), config.getBrightnessLeve());
            count++;
        }
        //黑白效果
        if (config.isNeedGrayscale()) {
            transformation[count] = new GrayscaleTransformation(config.getContext());
            count++;
        }

        if (config.isNeedFilteColor()) {
            transformation[count] = new ColorFilterTransformation(config.getContext(), config.getFilteColor());
            count++;
        }
        //漩涡
        if (config.isNeedSwirl()) {
            transformation[count] = new SwirlFilterTransformation(config.getContext(), 0.5f, 1.0f, new PointF(0.5f, 0.5f));
            count++;
        }
        //油画
        if (config.isNeedToon()) {
            transformation[count] = new ToonFilterTransformation(config.getContext());
            count++;
        }
        //墨画
        if (config.isNeedSepia()) {
            transformation[count] = new SepiaFilterTransformation(config.getContext());
            count++;
        }
        //锐化
        if (config.isNeedContrast()) {
            transformation[count] = new ContrastFilterTransformation(config.getContext(), config.getContrastLevel());
            count++;
        }
        //胶片
        if (config.isNeedInvert()) {
            transformation[count] = new InvertFilterTransformation(config.getContext());
            count++;
        }
        //马赛克
        if (config.isNeedPixelation()) {
            transformation[count] =new PixelationFilterTransformation(config.getContext(), config.getPixelationLevel());
            count++;
        }
        //素描
        if (config.isNeedSketch()) {
            transformation[count] =new SketchFilterTransformation(config.getContext());
            count++;
        }
        //晕映
        if (config.isNeedVignette()) {
            transformation[count] =new VignetteFilterTransformation(config.getContext(), new PointF(0.5f, 0.5f),
                    new float[] { 0.0f, 0.0f, 0.0f }, 0f, 0.75f);
            count++;
        }

        switch (config.getShapeMode()) {
            case ShapeMode.RECT:
                break;
            case ShapeMode.RECT_ROUND://圆角
                transformation[count] = new RoundedCornersTransformation
                        (config.getContext(), config.getRectRoundRadius(), 0, RoundedCornersTransformation.CornerType.ALL);
                count++;
                break;
            case ShapeMode.OVAL://圆形
                transformation[count] = new CropCircleTransformation(config.getContext());
                count++;
                break;
            case ShapeMode.SQUARE://正方形
                transformation[count] = new CropSquareTransformation(config.getContext());
                count++;
                break;
        }
        //做特效处理
        if (transformation.length != 0) {
            drawableTypeRequest.bitmapTransform(transformation);

        }
    }

    /**
     * 做样式操作统计
     * @param config
     * @return
     */
    private int statisticsCount(SingleConfig config) {
        int count = 0;
        //图片样式 椭圆 圆角 正方形
        if (config.getShapeMode() == ShapeMode.OVAL || config.getShapeMode() == ShapeMode.RECT_ROUND || config.getShapeMode() == ShapeMode.SQUARE) {
            count++;
        }
        //模糊
        if (config.isNeedBlur()) {
            count++;
        }
        //
        if (config.isNeedFilteColor()) {
            count++;
        }
        //亮度
        if (config.isNeedBrightness()) {
            count++;
        }
        //黑色
        if (config.isNeedGrayscale()) {
            count++;
        }
        //漩涡
        if (config.isNeedSwirl()) {
            count++;
        }
        //油画
        if (config.isNeedToon()) {
            count++;
        }
        //墨画
        if (config.isNeedSepia()) {
            count++;
        }
        //锐化
        if (config.isNeedContrast()) {
            count++;
        }
        //胶片
        if (config.isNeedInvert()) {
            count++;
        }
        //马赛克
        if (config.isNeedPixelation()) {
            count++;
        }
        //素描
        if (config.isNeedSketch()) {
            count++;
        }
        //晕映
        if (config.isNeedVignette()) {
            count++;
        }
        return count;
    }
    /**
     * 设置及请求 来源 网络 文件 asses res..
     * @param config
     * @param requestManager
     * @return
     *
     */
    private DrawableTypeRequest getDrawableTypeRequest(SingleConfig config, RequestManager requestManager) {
        //TODo 该地方还可以进行优化
        DrawableTypeRequest request = null;
        if (!TextUtils.isEmpty(config.getUrl())) {//http://
            request = requestManager.load(ImageUtil.appendUrl(config.getUrl()));
            Log.e("TAG","getUrl : "+config.getUrl());
        } else if (!TextUtils.isEmpty(config.getFilePath())) {//file://
            request = requestManager.load(ImageUtil.appendUrl(config.getFilePath()));
            Log.e("TAG","getFilePath : "+config.getFilePath());
        } else if (!TextUtils.isEmpty(config.getContentProvider())) {//content://
            request = requestManager.loadFromMediaStore(Uri.parse(config.getContentProvider()));
            Log.e("TAG","getContentProvider : "+config.getContentProvider());
        } else if (config.getResId() > 0) {//资源
            request = requestManager.load(config.getResId());
            Log.e("TAG","getResId : "+config.getResId());
        } else if(config.getFile() != null){//file
            request = requestManager.load(config.getFile());
            Log.e("TAG","getFile : "+config.getFile());
        } else if(!TextUtils.isEmpty(config.getAssertspath())){//assert
            request = requestManager.load(config.getAssertspath());
            Log.e("TAG","getAssertspath : "+config.getAssertspath());
        } else if(!TextUtils.isEmpty(config.getRawPath())){//raw
            request = requestManager.load(config.getRawPath());
            Log.e("TAG","getRawPath : "+config.getRawPath());
        }
        return request;
    }

    /**
     * 停止图片的加载
     */
    @Override
    public void pause() {
        Glide.with(context).pauseRequests();
    }

    /**
     * 恢复图片加载
     */
    @Override
    public void resume() {
        Glide.with(context).resumeRequests();
    }

    /**
     * 清理磁盘缓存
     */
    @Override
    public void clearDiskCache() {
        Glide.get(context).clearDiskCache();
    }

    /**
     * 清楚该view的缓存
     * @param view 指定view
     */
    @Override
    public void clearMemoryCache(View view) {
        Glide.clear(view);
    }

    /**
     * 清理内存缓冲
     */
    @Override
    public void clearMemory() {
        Glide.get(context).clearMemory();
    }

    /**
     * 是否缓存
     * @param url
     * @return
     */
    @Override
    public boolean isCached(String url) {
        return false;
    }

    /**
     * 当系统内存不足时用于清理内存缓存
     * //该参数有以下几种粒度：（具体见源码）

     static final int TRIM_MEMORY_COMPLETE = 80;

     static final int TRIM_MEMORY_MODERATE = 60;

     static final int TRIM_MEMORY_BACKGROUND = 40;

     static final int TRIM_MEMORY_UI_HIDDEN = 20;

     static final int TRIM_MEMORY_RUNNING_CRITICAL = 15;

     static final int TRIM_MEMORY_RUNNING_LOW = 10;

     static final int TRIM_MEMORY_RUNNING_MODERATE = 5;
     * @param level
     */
    @Override
    public void trimMemory(int level) {
        Glide.with(context).onTrimMemory(level);
    }

    /**
     * 清理所有缓存
     */
    @Override
    public void clearAllMemoryCaches() {
        Glide.with(context).onLowMemory();
    }

    /**
     * 开启线程下载bitmap
     * @param downLoadImageService
     */
    @Override
    public void saveImageIntoGallery(DownLoadImageService downLoadImageService) {
        new Thread(downLoadImageService).start();
    }
    /**
     * 设置占位图片 只有在网络请求 且没有该网络图片缓存的情况下 才生效
     * @param config
     * @return
     */
    public static boolean shouldSetPlaceHolder(SingleConfig config) {
        if (config.getPlaceHolderResId() <= 0) {
            return false;
        }
        if (config.getResId() > 0 || !TextUtils.isEmpty(config.getFilePath()) || GlobalConfig.getiLoader().isCached(config.getUrl())) {
            return false;
        } else {//只有在图片源为网络图片,并且图片没有缓存到本地时,才给显示placeholder
            return true;
        }
    }


}
