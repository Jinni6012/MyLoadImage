package com.example.imageloadaerlibrary.config;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.AnyRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RawRes;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.ViewPropertyAnimation;
import com.example.imageloadaerlibrary.utils.ImageUtil;

import java.io.File;

import static com.example.imageloadaerlibrary.config.Contants.ANDROID_RESOURCE;
import static com.example.imageloadaerlibrary.config.Contants.ASSERTS_PATH;
import static com.example.imageloadaerlibrary.config.Contants.RAW;


/**
 * author : daiwenbo
 * e-mail : daiwwenb@163.com
 * date   : 2017/6/10
 * description   : 图片加载相关参数设置实体类
 */

public class SingleConfig {
    private Context context;
    private boolean ignoreCertificateVerify;
    private String url;
    /*缩略图*/
    private float thumbnail;
    /*文件路径*/
    private String filePath;
    /*文件*/
    private File file;
    /*资源id*/
    private int resId;
    /*rwa路径*/
    private String rawPath;
    /*asserts路径*/
    private String assertspath;
    /*内容提供者*/
    private String contentProvider;
    /*只获bitmap*/
    private boolean asBitmap;
    /*是否gif*/
    private boolean gif;
    /*加载图片的View*/
    private View target;

    private int width;
    private int height;

    private int oWidth;
    private int oHeight;

    //滤镜
    private boolean isNeedVignette; //是否需要晕映
    private boolean isNeedSketch; //是否需要素描
    private float pixelationLevel; //是否需要马赛克
    private boolean isNeedPixelation; //是否需要马赛克
    private boolean isNeedInvert; //是否需要胶片
    public float contrastLevel;  //锐化等级
    private boolean isNeedContrast; //是否需要锐化
    private boolean isNeedSepia; //是否需要墨画
    private boolean isNeedToon; //是否需要油画
    private boolean isNeedSwirl;  // 是否需要漩涡
    private boolean isNeedGrayscale; //是否需要黑色
    private boolean isNeedBrightness; //是否需要亮度
    private float brightnessLeve; //是否需要亮度
    private boolean needBlur;//是否需要模糊
    private boolean needFilteColor;
    /*滤镜颜色*/
    private int filteColor;

    /*请求优先级*/
    private int priority;
    /*动画类型*/
    private int animationType;
    /*动画ID*/
    private int animationId;
    /*加载动画*/
    private Animation animation;
    /*动画资源*/
    private ViewPropertyAnimation.Animator animator;
    /*图片圆角*/
    private int blurRadius;
    private int placeHolderResId;
    /**
     *DiskCacheStrategy.NONE //什么都不缓存，就像刚讨论的那样
     DiskCacheStrategy.SOURCE //仅仅只缓存原来的全分辨率的图像
     DiskCacheStrategy.RESULT //仅仅缓存最终的图像，即，降低分辨率后的（或者是转换后的）
     DiskCacheStrategy.ALL //缓存所有版本的图像（默认行为）
     *
     * 设置磁盘缓存策略*/
    private DiskCacheStrategy diskCacheStrategy;
    /*填充模式 可选fitXY,centerInside.*/
    private int scaleMode;
    private int errorResId;

    private int shapeMode;
    private int rectRoundRadius;
    private BitmapListener bitmapListener;

    public SingleConfig(ConfigBuilder builder) {
        this.context = builder.context;
        this.url = builder.url;
        this.thumbnail = builder.thumbnail;
        this.filePath = builder.filePath;
        this.file = builder.file;
        this.resId = builder.resId;
        this.rawPath = builder.rawPath;
        this.assertspath = builder.assertspath;
        this.contentProvider = builder.contentProvider;

        this.ignoreCertificateVerify = builder.ignoreCertificateVerify;

        this.target = builder.target;

        this.width = builder.width;
        this.height = builder.height;

        this.oWidth = builder.oWidth;
        this.oHeight = builder.oHeight;

        this.shapeMode = builder.shapeMode;
        if (shapeMode == ShapeMode.RECT_ROUND) {
            this.rectRoundRadius = builder.rectRoundRadius;
        }
        this.scaleMode = builder.scaleMode;

        this.diskCacheStrategy = builder.diskCacheStrategy;

        this.animationId = builder.animationId;
        this.animationType = builder.animationType;
        this.animator = builder.animator;
        this.animation = builder.animation;

        this.priority = builder.priority;
        //滤镜
        this.isNeedVignette = builder.isNeedVignette; //是否需要晕映
        this.isNeedSketch = builder.isNeedSketch; //是否需要素描
        this.pixelationLevel = builder.pixelationLevel; //是否需要马赛克
        this.isNeedPixelation = builder.isNeedPixelation; //是否需要马赛克
        this.isNeedInvert = builder.isNeedInvert; //是否需要胶片
        this.contrastLevel = builder.contrastLevel; //锐化等级
        this.isNeedContrast = builder.isNeedContrast; //是否需要锐化
        this.isNeedSepia = builder.isNeedSepia; //是否需要亮度
        this.isNeedToon = builder.isNeedToon; //是否需要亮度
        this.isNeedSwirl = builder.isNeedSwirl; //是否需要亮度
        this.isNeedGrayscale = builder.isNeedGrayscale; //是否需要黑色
        this.isNeedBrightness = builder.isNeedBrightness; //是否需要亮度
        this.brightnessLeve = builder.brightnessLeve; //是否需要亮度
        this.filteColor = builder.filteColor;
        this.needBlur = builder.needBlur;
        this.needFilteColor = builder.needFilteColor;
        this.placeHolderResId = builder.placeHolderResId;

        this.asBitmap = builder.asBitmap;
        this.bitmapListener = builder.bitmapListener;
        this.gif = builder.gif;
        this.blurRadius = builder.blurRadius;
        this.errorResId = builder.errorResId;
    }

    public Context getContext() {
        return context;
    }

    public boolean isIgnoreCertificateVerify() {
        return ignoreCertificateVerify;
    }

    public String getUrl() {
        return url;
    }

    public float getThumbnail() {
        return thumbnail;
    }

    public String getFilePath() {
        return filePath;
    }

    public File getFile() {
        return file;
    }

    public int getResId() {
        return resId;
    }

    public String getRawPath() {
        return rawPath;
    }

    public String getAssertspath() {
        return assertspath;
    }

    public String getContentProvider() {
        return contentProvider;
    }

    public boolean isAsBitmap() {
        return asBitmap;
    }

    public boolean isGif() {
        return gif;
    }

    public View getTarget() {
        return target;
    }

    public int getWidth() {
        if(width<=0){
            if(target!=null){
                width=target.getMeasuredWidth();
            }
            if(width<=0){
                width= ImageUtil.getWinWidth();
            }
        }
        return width;
    }
    public int getHeight(){
        if(height<=0){
            if(target!=null){
               height=target.getMeasuredHeight();
            }
            if(height<=0){
                height=ImageUtil.getWinHeight();
            }
        }
        return height;
    }

    public int getoWidth() {
        return oWidth;
    }

    public int getoHeight() {
        return oHeight;
    }

    public boolean isNeedVignette() {
        return isNeedVignette;
    }

    public boolean isNeedSketch() {
        return isNeedSketch;
    }

    public float getPixelationLevel() {
        return pixelationLevel;
    }

    public boolean isNeedPixelation() {
        return isNeedPixelation;
    }

    public boolean isNeedInvert() {
        return isNeedInvert;
    }

    public float getContrastLevel() {
        return contrastLevel;
    }

    public boolean isNeedContrast() {
        return isNeedContrast;
    }

    public boolean isNeedSepia() {
        return isNeedSepia;
    }

    public boolean isNeedToon() {
        return isNeedToon;
    }

    public boolean isNeedSwirl() {
        return isNeedSwirl;
    }

    public boolean isNeedGrayscale() {
        return isNeedGrayscale;
    }

    public boolean isNeedBrightness() {
        return isNeedBrightness;
    }

    public float getBrightnessLeve() {
        return brightnessLeve;
    }

    public boolean isNeedBlur() {
        return needBlur;
    }

    public boolean isNeedFilteColor() {
        return needFilteColor;
    }

    public int getFilteColor() {
        return filteColor;
    }

    public int getPriority() {
        return priority;
    }

    public int getAnimationType() {
        return animationType;
    }

    public int getAnimationId() {
        return animationId;
    }

    public Animation getAnimation() {
        return animation;
    }

    public ViewPropertyAnimation.Animator getAnimator() {
        return animator;
    }

    public int getBlurRadius() {
        return blurRadius;
    }

    public int getPlaceHolderResId() {
        return placeHolderResId;
    }

    public DiskCacheStrategy getDiskCacheStrategy() {
        return diskCacheStrategy;
    }

    public int getScaleMode() {
        return scaleMode;
    }

    public int getErrorResId() {
        return errorResId;
    }

    public int getShapeMode() {
        return shapeMode;
    }

    public int getRectRoundRadius() {
        return rectRoundRadius;
    }

    public BitmapListener getBitmapListener() {
        return bitmapListener;
    }

    /**
     * 设置Glide 加载图片参数
     */
    private void show() {
        GlobalConfig.getiLoader().request(this);
    }
    /**
     * 加载bitmap监听
     */
    public interface BitmapListener{
        void onSuccess(Bitmap bitmap);
        void onFail();
    }
    public static class ConfigBuilder{
        private Context context;
        /*https是否忽略校验*/
        private boolean ignoreCertificateVerify=GlobalConfig.ignoreCertificateVerify;
        /**url
         * 网络图片：http://
         * 本地图片：file://
         * 内容提供者： cotent://ContenResolver
         * asset目录：asset://AssetManager
         * res目录: res:// Resources.openRawResource
         * Uri中指定的图片数据：data:mime/type;base64
         */
        private String url;
        /*缩略图*/
        private float thumbnail;
        /*文件路径*/
        private String filePath;
        /*文件*/
        private File file;
        /*资源id*/
        private int resId;
        /*rwa路径*/
        private String rawPath;
        /*asserts路径*/
        private String assertspath;
        /*内容提供者*/
        private String contentProvider;
        /*只获bitmap*/
        private boolean asBitmap;
        /*是否gif*/
        private boolean gif;
        /*加载图片的View*/
        private View target;

        private int width;
        private int height;

        /*加载图片分辨率的宽高*/
        private int oWidth;
        private int oHeight;

        //滤镜
        private boolean isNeedVignette; //是否需要晕映
        private boolean isNeedSketch; //是否需要素描
        private float pixelationLevel; //是否需要马赛克
        private boolean isNeedPixelation; //是否需要马赛克
        private boolean isNeedInvert; //是否需要胶片
        public float contrastLevel;  //锐化等级
        private boolean isNeedContrast; //是否需要锐化
        private boolean isNeedSepia; //是否需要墨画
        private boolean isNeedToon; //是否需要油画
        private boolean isNeedSwirl;  // 是否需要漩涡
        private boolean isNeedGrayscale; //是否需要黑色
        private boolean isNeedBrightness; //是否需要亮度
        private float brightnessLeve; //是否需要亮度
        private boolean needBlur;//是否需要模糊
        private boolean needFilteColor;//是否需要模糊
        /*滤镜颜色*/
        private int filteColor;

        /*请求优先级*/
        private int priority;
        /*动画类型*/
        private int animationType;
        /*动画ID*/
        private int animationId;
        /*加载动画*/
        private Animation animation;
        /*动画资源*/
        private ViewPropertyAnimation.Animator animator;
        /*图片圆角*/
        private int blurRadius;
        /*占位图*/
        private int placeHolderResId;
        /*跳过磁盘存储*/
        private DiskCacheStrategy diskCacheStrategy;
        /*填充模式 可选fitXY,centerInside.*/
        private int scaleMode;
        /*加载错误显示的图片*/
        private int errorResId;

        private int shapeMode;
        private int rectRoundRadius;
        private BitmapListener bitmapListener;

        public ConfigBuilder(@NonNull Context context) {
            this.context = context;
        }
        /**
         * 是否忽略https
         * @param ignoreCertificateVerify
         * @return
         */
        public ConfigBuilder ignoreCertificateVerify(boolean ignoreCertificateVerify) {
            this.ignoreCertificateVerify = ignoreCertificateVerify;
            return this;
        }

        /**
         * 缩略图
         * @param thumbnail
         * @return
         */
        public ConfigBuilder thumbnail(float thumbnail) {
            this.thumbnail = thumbnail;
            return this;
        }

        /**
         * error图
         *
         * @param errorResId
         * @return
         */
        public ConfigBuilder error(@IdRes @NonNull int errorResId) {
            this.errorResId = errorResId;
            return this;
        }

        /**
         * 设置网络路径
         * @param url
         * @return
         */
        public ConfigBuilder url(@NonNull String url) {
            this.url = url;
            if (url.contains("gif")) {
                gif = true;
            }
            return this;
        }

        /**
         * 加载SD卡资源
         *
         * @param filePath
         * @return
         */
        public ConfigBuilder file(@NonNull String filePath) {
            File file = new File(filePath);
            if (!file.exists()) {
                Log.e("imageloader", "图片文件不存在");
                return this;
            }
            if (!filePath.startsWith("file://")) {
                filePath+="file://";
            }
            if (filePath.contains("gif")) {
                gif = true;
            }
            this.filePath = filePath;
            return this;
        }

        /**
         * 加载SD卡资源
         *
         * @param file
         * @return
         */
        public ConfigBuilder file(@NonNull File file) {
            if (!file.exists()) {
                Log.e("imageloader", "图片文件不存在");
                return this;
            }
            this.file = file;
            return this;
        }

        /**
         * 加载drawable资源
         *
         * @param resId
         * @return
         */
        public ConfigBuilder res(@DrawableRes int resId) {
            this.resId = resId;
            return this;
        }

        /**
         * 加载ContentProvider资源
         * @param contentProvider
         * @return
         */
        public ConfigBuilder content(@NonNull String contentProvider) {
            if (contentProvider.startsWith("content:")) {
                this.contentProvider = contentProvider;
                if (contentProvider.contains("gif")) {
                    gif = true;
                }
                return this;
            }
            Log.e("imageloader", "内容提供者路径错误");
            return this;
        }

        /**
         * 加载raw资源
         *
         * @param raw
         * @return
         */
        public ConfigBuilder raw(@RawRes int raw) {
            String path = ANDROID_RESOURCE + context.getPackageName() + RAW + raw;
            this.rawPath = path;
            if (rawPath.contains("gif")) {
                gif = true;
            }
            return this;
        }

        /**
         * 加载asserts资源
         *
         * @param path
         * @return
         */
        public ConfigBuilder asserts(@NonNull String path) {
            this.assertspath =ASSERTS_PATH + path;
            if (assertspath.contains("gif")) {
                gif = true;
            }
            return this;
        }

        /**
         * 开始使用Glide去加载图片
         * @param targetView
         */
        public void into(@NonNull View targetView) {
            this.target = targetView;
            new SingleConfig(this).show();
        }

        /**
         * 下载bitmap
         * @param bitmapListener 回调监听
         */
        public void asBitmap(@NonNull BitmapListener bitmapListener) {
            this.bitmapListener = ImageUtil.getBitmapListenerProxy(bitmapListener);
            this.asBitmap = true;
            new SingleConfig(this).show();
        }

        /**
         * 加载图片的分辨率
         *
         * @param oWidth
         * @param oHeight
         * @return
         */
        public ConfigBuilder override(int oWidth, int oHeight) {
            this.oWidth = ImageUtil.dip2px(oWidth);
            this.oHeight = ImageUtil.dip2px(oHeight);
            return this;
        }

        /**
         * 占位图
         *
         * @param placeHolderResId
         * @return
         */
        public ConfigBuilder placeHolder(@AnyRes int placeHolderResId) {
            this.placeHolderResId = placeHolderResId;
            return this;
        }


        /**
         * 是否需要高斯模糊
         * @return
         */
        public ConfigBuilder blur(int blurRadius) {
            this.needBlur = true;
            this.blurRadius = blurRadius;
            return this;
        }

        /**
         * 圆形
         * @return
         */
        public ConfigBuilder asCircle() {
            this.shapeMode = ShapeMode.OVAL;
            return this;
        }

        /**
         * 形状为圆角矩形时的圆角半径
         * @param rectRoundRadius
         * @return
         */
        public ConfigBuilder rectRoundCorner(@Nullable int rectRoundRadius) {
            this.rectRoundRadius = ImageUtil.dip2px(rectRoundRadius);
            this.shapeMode = ShapeMode.RECT_ROUND;//圆角
            return this;
        }


        /**
         * 正方形
         * @return
         */
        public ConfigBuilder asSquare() {
            this.shapeMode = ShapeMode.SQUARE;
            return this;
        }


        /**
         * 磁盘缓存策略
         * DiskCacheStrategy.NONE :不缓存图片
         * DiskCacheStrategy.SOURCE :缓存图片源文件
         * DiskCacheStrategy.RESULT:缓存修改过的图片
         * DiskCacheStrategy.ALL:缓存所有的图片，默认
         */
        public ConfigBuilder diskCacheStrategy(DiskCacheStrategy diskCacheStrategy) {
            this.diskCacheStrategy = diskCacheStrategy;
            return this;
        }

        /**
         * 拉伸/裁剪模式
         *
         * @param scaleMode 取值ScaleMode
         * @return
         */
        public ConfigBuilder scale(int scaleMode) {
            this.scaleMode = scaleMode;
            return this;
        }


        public ConfigBuilder animate(int animationId) {
            this.animationType = AnimationMode.ANIMATIONID;
            this.animationId = animationId;
            return this;
        }

        public ConfigBuilder animate(ViewPropertyAnimation.Animator animator) {
            this.animationType = AnimationMode.ANIMATOR;
            this.animator = animator;
            return this;
        }

        public ConfigBuilder animate(Animation animation) {
            this.animationType = AnimationMode.ANIMATION;
            this.animation = animation;
            return this;
        }

        public ConfigBuilder priority(int priority) {
            this.priority = priority;
            return this;
        }

        public ConfigBuilder colorFilter(int filteColor) {
            this.filteColor = filteColor;
            this.needFilteColor = true;
            return this;
        }

        public ConfigBuilder brightnessFilter(float level) {
            this.isNeedBrightness = true;
            this.brightnessLeve = level;
            return this;
        }

        public ConfigBuilder grayscaleFilter() {
            this.isNeedGrayscale = true;
            return this;
        }

        public ConfigBuilder swirlFilter() {
            this.isNeedSwirl = true;
            return this;
        }

        public ConfigBuilder toonFilter() {
            this.isNeedToon = true;
            return this;
        }

        public ConfigBuilder sepiaFilter() {
            this.isNeedSepia = true;
            return this;
        }

        public ConfigBuilder contrastFilter(float constrasrLevel) {
            this.contrastLevel = constrasrLevel;
            this.isNeedContrast = true;
            return this;
        }

        public ConfigBuilder invertFilter() {
            this.isNeedInvert = true;
            return this;
        }

        public ConfigBuilder pixelationFilter(float pixelationLevel) {
            this.pixelationLevel = pixelationLevel;
            this.isNeedPixelation = true;
            return this;
        }

        public ConfigBuilder sketchFilter() {
            this.isNeedSketch = true;
            return this;
        }

        public ConfigBuilder vignetteFilter() {
            this.isNeedVignette = true;
            return this;
        }


    }
}
