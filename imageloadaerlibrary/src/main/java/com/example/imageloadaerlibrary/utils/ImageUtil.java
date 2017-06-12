package com.example.imageloadaerlibrary.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.WindowManager;

import com.example.imageloadaerlibrary.config.GlobalConfig;
import com.example.imageloadaerlibrary.config.SingleConfig;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import static com.example.imageloadaerlibrary.config.GlobalConfig.context;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * author : daiwenbo
 * e-mail : daiwwenb@163.com
 * date   : 2017/6/10
 * description   : 工具类
 */

public class ImageUtil {
    private static final String BASE_URL="http://";
    /*转换为px*/
    public static int dip2px(float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
    /*通过反射 返回bitmap 回调监听*/
    public static SingleConfig.BitmapListener getBitmapListenerProxy(final SingleConfig.BitmapListener bitmapListener) {
        return (SingleConfig.BitmapListener) Proxy.newProxyInstance(SingleConfig.class.getClassLoader(),
                bitmapListener.getClass().getInterfaces(), new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, final Method method, final Object[] args) throws Throwable {
                        runOnUIThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Object object = method.invoke(bitmapListener, args);
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                } catch (InvocationTargetException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        return null;
                    }
                });
    }
    public static void runOnUIThread(Runnable runnable){
        GlobalConfig.getMainHandler().post(runnable);
    }

    /**
     * 获取 屏幕的宽高
     * @return
     */
    public static int getWinHeight() {
        WindowManager wm = (WindowManager) GlobalConfig.context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int winWidth = size.x;
        int winHeight = size.y;
        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return winHeight < winWidth ? winHeight : winWidth;
        } else if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            return winHeight > winWidth ? winHeight : winWidth;
        }
        return winHeight;
    }

    public static int getWinWidth() {
        WindowManager wm = (WindowManager) GlobalConfig.context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int winWidth = size.x;
        int winHeight = size.y;
        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return winHeight > winWidth ? winHeight : winWidth;
        } else if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            return winHeight < winWidth ? winHeight : winWidth;
        }
        return winWidth;
    }
    public static String appendUrl(@NonNull String url) {
        checkNotNull(url, "请求图片资源路径不能为空");
        String newUrl = url;
        boolean hasHost = newUrl.startsWith("http:") || newUrl.startsWith("https:");
        if (!hasHost) {
                newUrl = BASE_URL+ url;
        }

        return newUrl;
    }


}
