package com.bluestrom.gao.explorersogoupics.application;

import android.content.Context;

import com.bluestrom.gao.explorersogoupics.util.AppLog;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by Gao-Krund on 2017/1/23.
 */

public class PicsUncaughtException implements Thread.UncaughtExceptionHandler {

    private static final String TAG = "PicsUncaughtException";

    private Thread.UncaughtExceptionHandler mDefaultExceptionHandler;

    private static PicsUncaughtException instance = new PicsUncaughtException();

    private Context mContext;

    private PicsUncaughtException() {
    }

    public static PicsUncaughtException getInstance() {
        return instance;
    }


    public void inti(Context context) {
        mContext = context;
        mDefaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        StringWriter stackTrace = new StringWriter();
        e.printStackTrace(new PrintWriter(stackTrace));
        AppLog.e(TAG, stackTrace.toString());
        System.exit(-1);
    }
}
