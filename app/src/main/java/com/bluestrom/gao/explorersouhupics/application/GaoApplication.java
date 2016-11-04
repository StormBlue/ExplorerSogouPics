package com.bluestrom.gao.explorersouhupics.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

import com.bluestrom.gao.explorersouhupics.util.Const;
import com.bluestrom.gao.explorersouhupics.util.Pub;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.Stack;

public class GaoApplication extends Application {

    private static GaoApplication instance = null;
    private static Stack<Activity> activityStack;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Fresco.initialize(this);
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point point = Pub.getDisplaySize(display);
        Const.SCREEN_WIDTH = point.x;
        Const.SCREEN_HEIGHT = point.y;
    }

    public static GaoApplication getInstance() {
        return instance;
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
