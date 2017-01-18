package com.bluestrom.gao.explorersouhupics.util;

import android.util.Log;

import org.apache.log4j.Level;

import java.io.File;

import de.mindpipe.android.logging.log4j.LogConfigurator;

public class ConfigureLog4j {
    private static final int MAX_FILE_SIZE = 1024 * 1024 * 5;
    private static final String DEFAULT_LOG_FILE_NAME = "explorer.log";

    private static final String TAG = "ConfigureLog4j";

    public static void configure(String fileName) {
        final LogConfigurator logConfigurator = new LogConfigurator();
        try {
            logConfigurator.setFileName(Pub.getSavePath() + File.separator + "logs"
                    + File.separator + fileName);
            logConfigurator.setMaxBackupSize(3);
            logConfigurator.setRootLevel(Level.DEBUG);
            logConfigurator.setFilePattern("%d\t%p/%c:\t%m%n");
            logConfigurator.setMaxFileSize(MAX_FILE_SIZE);
            logConfigurator.setImmediateFlush(true);
            logConfigurator.configure();
            Log.i(TAG, "Log4j config finish");
        } catch (Throwable throwable) {
            logConfigurator.setResetConfiguration(true);
            Log.e(TAG, "Log4j config error, use default config. Error:" + throwable);
        }
    }

    public static void configure() {
        configure(DEFAULT_LOG_FILE_NAME);
    }
}