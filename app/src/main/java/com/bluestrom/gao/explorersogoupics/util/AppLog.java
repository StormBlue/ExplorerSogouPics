package com.bluestrom.gao.explorersogoupics.util;

import org.apache.log4j.Logger;

public class AppLog {

    public static final boolean SWITCH_LOG = true;

    private static boolean isConfigured = false;

    public static void d(String tag, String message) {
        if (SWITCH_LOG) {
            Logger LOGGER = getLogger(tag);
            LOGGER.debug(message);
        }
    }

    public static void d(String tag, String message, Throwable exception) {
        if (SWITCH_LOG) {
            Logger LOGGER = getLogger(tag);
            LOGGER.debug(message, exception);
        }
    }

    public static void i(String tag, String message) {
        if (SWITCH_LOG) {
            Logger LOGGER = getLogger(tag);
            LOGGER.info(message);
        }
    }

    public static void i(String tag, String message, Throwable exception) {
        if (SWITCH_LOG) {
            Logger LOGGER = getLogger(tag);
            LOGGER.info(message, exception);
        }
    }

    public static void w(String tag, String message) {
        if (SWITCH_LOG) {
            Logger LOGGER = getLogger(tag);
            LOGGER.warn(message);
        }
    }

    public static void w(String tag, String message, Throwable exception) {
        if (SWITCH_LOG) {
            Logger LOGGER = getLogger(tag);
            LOGGER.warn(message, exception);
        }
    }

    public static void e(String tag, String message) {
        if (SWITCH_LOG) {
            Logger LOGGER = getLogger(tag);
            LOGGER.error(message);
        }
    }

    public static void e(String tag, String message, Throwable exception) {
        if (SWITCH_LOG) {
            Logger LOGGER = getLogger(tag);
            LOGGER.error(message, exception);
        }
    }

    private static Logger getLogger(String tag) {
        if (!isConfigured) {
            ConfigureLog4j.configure();
            isConfigured = true;
        }
        Logger logger;
        if (null == tag || null != tag && tag.isEmpty()) {
            logger = Logger.getRootLogger();
        } else {
            logger = Logger.getLogger(tag);
        }
        return logger;
    }
}
