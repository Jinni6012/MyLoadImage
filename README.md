# MyLoadImage
针对图片加载的二次封装
#####前言：
在迎合公司一些特殊的需求 以及开发的时候尽量做模块化开发 才有了本次的针对 Glide的再次封装 其实Glide的封装已经很好了 再度封装的好处还有：

- 避免以后换框架的时候需要改的地方太多。如果封装了只需要改封装的方法而不会影响到所有的代码。
- 入口统一，所有图片加载都在这一个地方管理，一目了然，即使有什么改动我也只需要改这一个类就可以了。
- 虽然现在的第三方库已经非常好用，但是如果我们看到第三方库就拿来用的话，很可能在第三方库无法满足业务需求或者停止维护的时候，发现替换库，工作量可见一斑。这就是不封装在切库时面临的窘境！
- 外部表现一致，内部灵活处理原则
 
 另外本次的封装很大程度上参考了https://github.com/libin7278/ImageLoader 在这里感谢该作者提供的参考 另外因为公司进度比较紧张 可能有些地方写的不是很好 同时也欢迎大家去做修改
 
 **本文是基于Glide 3.8版本封装的 现在有4.0开发版了且api有些变化 另外还不了解Glide的朋友 请点击传送门[Glide API](http://bumptech.github.io/glide/doc/generatedapi.html)**
 
 
#####结构图：
![](http://on96fbw9r.bkt.clouddn.com/g01.png)
本案例的的地址：[DEMO](https://github.com/daiwenbo88/MyLoadImage) **代码中注释很详细 欢迎查看使用**
#####使用:
**初始化**
在Application中初始化(自定义内存缓存,磁盘缓存等) 这里有2种方式：

1. 方式一：

 ```
 //MainApplication.this  ChannelConfig.GLIDE(图加载框架)
ImageLoader.init(this,ChannelConfig.GLIDE);
//MainApplication.this CACHE_IMAGE_SIZE磁盘缓存的大小（M） ChannelConfig.GLIDE(图加载框架)
ImageLoader.init(context,CACHE_IMAGE_SIZE,channel);
//MemoryCategory.NORMAL 动态缓存策略
ImageLoader.init(context,cacheSize, MemoryCategory.NORMAL,channel);
//true 磁盘缓存放到应用目录下 false 缓存放到SD卡中
ImageLoader.init(context,cacheSize, MemoryCategory.NORMAL,true,channel);
 ```
2. 方式二：

 ```
 mageLoader.create()
                .fileName("image_cache")//自定义图片缓存目录
                .channel(ChannelConfig.GLIDE)//设置图片加载的渠 道框架这里是Gile
                .cacheSizeInM(100)//分配缓存空间 100*1024*1024
                .asImternalcd(true)// true 目录/ false sd卡
                .memoryCategory(MemoryCategory.HIGH)//动态缓存策略
                .init(this);
 ```
 
#####加载图片 
 1.加载网络图片
 
 ```
 ImageLoader.with(this)
                .url(URL1)//url
                .animate(animationObject)//加载动画
                .thumbnail(1.0f)//缩略图
                .scale(ScaleMode.CENTER_CROP)//图片拉伸格式
                .rectRoundCorner(20)//设置图片圆角
                .into(iv_test1);
 ```
![](http://on96fbw9r.bkt.clouddn.com/g02.png)
2. 加载File图片

```
//file(String FilePath)
ImageLoader.with(this)
            .file("file://" + Environment.getExternalStorageDirectory().getPath() + File.separatorChar + IMG_NAME)
            .placeHolder(R.mipmap.ic_launcher)//占为图
            .scale(ScaleMode.FIT_CENTER)//拉伸模式
            .into(iv_test11);
//file(File file)
ImageLoader.with(this)
            .file(new File(getFilesDir(), IMG_NAME_C))
            .placeHolder(R.mipmap.ic_launcher)
            .scale(ScaleMode.FIT_CENTER)
            .into(iv_test12);
```
3.**加载Drawable图片**
    **因为集成了transformations库 可以实现很多特效 比如 晕映 素描 马赛克 胶片 锐化 墨画 模糊等 代码中注释很详细 欢迎查看使用**
    
```
compile 'jp.wasabeef:glide-transformations:2.0.2'
compile 'jp.co.cyberagent.android.gpuimage:gpuimage-library:1.4.1'
```
```
ImageLoader.with(this)
                .res(R.drawable.b000)
                .vignetteFilter()//晕映
                .priority(PriorityMode.PRIORITY_NORMAL)//图片加载优先级
                .placeHolder(R.mipmap.ic_launcher)
                .scale(ScaleMode.FIT_CENTER)
                .into(iv_test9);
                
 ImageLoader.with(this)
            .res(R.drawable.b000)
            .sketchFilter()//素描
            .placeHolder(R.mipmap.ic_launcher)
            .scale(ScaleMode.FIT_CENTER)
            .into(iv_test10);
```
![](http://on96fbw9r.bkt.clouddn.com/g03.png)
4. **加载raw目录图片**

```
ImageLoader.with(this)
            .raw(R.raw.header)
            .placeHolder(R.mipmap.ic_launcher)
            .scale(ScaleMode.FIT_CENTER)
            .asCircle()//圆形
            .into(iv_test14);
            
ImageLoader.with(this)
            .raw(R.raw.header)
            .placeHolder(R.mipmap.ic_launcher)
            .scale(ScaleMode.FIT_CENTER)
            .diskCacheStrategy(DiskCacheStrategy.NONE)//不缓存
            .asSquare()//正方形
            .into(iv_test15);
```
![](http://on96fbw9r.bkt.clouddn.com/g04.png)
5. **加载assets目录图片**
 
```
ImageLoader.with(this)
                .asserts(IMG_NAME_C)
                .placeHolder(R.mipmap.ic_launcher)
                .scale(ScaleMode.FIT_CENTER)
                .rectRoundCorner(50)//圆角角度
                .into(iv_test13);
```
![](http://on96fbw9r.bkt.clouddn.com/g06.png)
6.**加载Gif**

```
ImageLoader.with(this)
                .res(R.drawable.gif_test)//gif
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)//缓存策略 SOURCE缓存变动后图片
                .placeHolder(R.mipmap.ic_launcher)
                .scale(ScaleMode.FIT_CENTER)
                .into(iv_test7);
                
ImageLoader.with(this)
                .url(URL4)//网络gif图片
                .diskCacheStrategy(DiskCacheStrategy.NONE)//NONE不缓存
                .into(iv_test3);
                
```
![](http://on96fbw9r.bkt.clouddn.com/gif_test.gif)

7.**下载网络图片**

```
//回调在主线程
ImageLoader.with(this)
            .url(URL3)
            .asBitmap(new SingleConfig.BitmapListener() {
                @Override
                public void onSuccess(Bitmap bitmap) {
                    Log.e(TAG, "下载图片成功 bitmap");
                    iv_test16.setImageBitmap(bitmap);
                }
                @Override
                public void onFail() {

                }
            });
```
```
 /**
   * 回调在分线程(下载完成默认保存到磁盘目录 若检测到有SD卡就在SD卡存储)
   * url
   * fileName 文件名
   * callBack 回调 在分线程 不能做UI操作
   */
ImageLoader.saveBitmapIntoGallery(new DownLoadImageService(MainActivity.this, URL3, "lala", new ImageDownLoadCallBack() {

            @Override
            public void onDownLoadSuccess(Bitmap bitmap) {
                Log.e(TAG, "下载图片成功 bitmap");
            }

            @Override
            public void onDownLoadFailed() {
                Log.e(TAG, "下载图片失败");
            }

        }));
```
8.**另外还提供了一些清理内存缓存 磁盘缓存的方法**

```
// 根据系统内存状态动态调整内存使用策略
ImageLoader.trimMemory(level);

// 清理所有缓存
ImageLoader.clearAllMemoryCaches();

 // 清理指定View的缓存
ImageLoader.clearMemoryCache(view);

 // 清理磁盘缓存
ImageLoader.clearDiskCache();

// 清理内存缓存
ImageLoader.clearMemory();
```
```
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
        //根据系统内存状态动态调整内存使用策略
        ImageLoader.trimMemory(level);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        ImageLoader.clearMemory();
    }
}
```

9.**针对滑动列表做内存优化 这里以 RecyclerView为例**：

```
ImageLoader.resumeRequest();//恢复图片的请求
ImageLoader.pauseRequest();//停止请求图片
```

```
//监听RecyclerView滑动操作
RecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                switch (newState){
                    case SCROLL_STATE_IDLE://屏幕停止滚动
                        Log.e(TAG,"恢复加载图片");
                        ImageLoader.resumeRequest();//恢复图片的请求
                        break;
                    case SCROLL_STATE_DRAGGING://手指还在屏幕上
                        Log.e(TAG,"停止加载图片");
                        ImageLoader.pauseRequest();//停止请求图片
                        break;
                    case SCROLL_STATE_SETTLING://用户操作 屏幕惯性滚动
                        Log.e(TAG,"停止加载图片");
                        ImageLoader.pauseRequest();
                        break;
                }
            }
        });
```  
![](http://on96fbw9r.bkt.clouddn.com/glide.gif)

 以上就是图片加载框架的初级封装，同时也欢迎大家斧正 

