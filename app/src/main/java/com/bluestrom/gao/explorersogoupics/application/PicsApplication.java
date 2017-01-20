package com.bluestrom.gao.explorersogoupics.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

import com.bluestrom.gao.explorersogoupics.util.Const;
import com.bluestrom.gao.explorersogoupics.util.Pub;
import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.util.ByteConstants;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

import java.io.File;
import java.util.Stack;

public class PicsApplication extends Application {

    private static PicsApplication instance = null;
    private static Stack<Activity> activityStack;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        init();
    }

    public static PicsApplication getInstance() {
        return instance;
    }

    private void init() {
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point point = Pub.getDisplaySize(display);
        Const.SCREEN_WIDTH = point.x;
        Const.SCREEN_HEIGHT = point.y;

        DiskCacheConfig mainDiskCacheConfig = DiskCacheConfig.newBuilder(instance)
                .setMaxCacheSize(200 * ByteConstants.MB)
                .setMaxCacheSizeOnLowDiskSpace(80 * ByteConstants.MB)
                .setMaxCacheSizeOnVeryLowDiskSpace(40 * ByteConstants.MB)
                .setBaseDirectoryName(Const.PICS_ORIGIN_CACHE_DIR)
                .setBaseDirectoryPath(new File(Pub.getSavePath()))
                .build();

        DiskCacheConfig smallImageDiskCacheConfig = DiskCacheConfig.newBuilder(instance)
                .setMaxCacheSize(80 * ByteConstants.MB)
                .setMaxCacheSizeOnLowDiskSpace(40 * ByteConstants.MB)
                .setMaxCacheSizeOnVeryLowDiskSpace(20 * ByteConstants.MB)
                .setBaseDirectoryName(Const.PICS_STHUMB_CACHE_DIR)
                .setBaseDirectoryPath(new File(Pub.getSavePath()))
                .build();

        ImagePipelineConfig pipelConfig = ImagePipelineConfig.newBuilder(instance)
                .setMainDiskCacheConfig(mainDiskCacheConfig)
                .setSmallImageDiskCacheConfig(smallImageDiskCacheConfig)
                .build();
        Fresco.initialize(this, pipelConfig);
    }

    /**
     * add Activity 添加Activity到栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * get current Activity 获取当前Activity（栈中最后一个压入的）
     */
    public Activity getCurrentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity（栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i) && !activityStack.get(i).isFinishing()) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 退出应用程序
     */
    public void AppExit() {
        try {
            // 如果用户没有登陆，在退出时就stop后台的socketservice
            finishAllActivity();
            android.os.Process.killProcess(android.os.Process.myPid());
            System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
