package com.bluestrom.gao.explorersogoupics.util;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.hardware.Camera;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.text.format.Time;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;

import com.bluestrom.gao.explorersogoupics.application.PicsApplication;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.lang.reflect.Method;
import java.net.NetworkInterface;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class Pub {

    private static final String TAG = "Pub";

    private static Gson gson = new Gson();

    public static final boolean mbIsDeBug = true;

    public static void LOG(String tag, String msg) {
        if (mbIsDeBug) {
            Log.i(tag, msg);
        }
    }

    public static Gson getGsonClient() {
        return gson;
    }

    /**
     * 通过Url获得文件名
     *
     * @param url
     * @return
     */
    public static String getFileNameFromUrl(String url) {
        int index = url.lastIndexOf('/');
        return url.substring(index + 1);
    }

    public static String getFormatRemainTime(long time) {
        String strTime = "";
        int day = (int) (time / (24 * 60 * 60));
        int hour = (int) ((time % (24 * 60 * 60)) / (60 * 60));
        int min = (int) ((time % (24 * 60 * 60)) % (60 * 60)) / 60;
        // if (day != 0) {
        // strTime += day + "天";
        // }
        // if (day != 0 || hour != 0) {
        // strTime += hour + "小时";
        // }
        // strTime += min + "分钟";
        // strTime += day<10?"0"+day+"天":day+"天";
        // strTime += hour<10?"0"+hour+"小时":hour+"小时";
        // strTime += min<10?"0"+min+"分钟":min+"分钟";
        if (day != 0) {
            strTime += day + "天";
            if (hour != 0) {
                strTime += hour + "小时";
            } else if (min != 0) {
                strTime += min + "分钟";
            }
        } else {
            if (hour != 0) {
                strTime += hour + "小时";
                if (min != 0) {
                    strTime += min + "分钟";
                }
            } else if (min != 0) {
                strTime += min + "分钟";
            }
        }
        return strTime;
    }

    /**
     * 判断timestamp与当前时间是否在同一周
     *
     * @param timestamp
     * @return
     */
    public static boolean isInSameWeek(long timestamp) {
        if (timestamp == 0) {
            return false;
        }
        Calendar calendar = Calendar.getInstance();
        long currMls = System.currentTimeMillis();
        calendar.setTimeInMillis(currMls);
        int currDay = calendar.get(Calendar.DAY_OF_WEEK);
        currDay = currDay == 1 ? 8 : currDay;
        calendar.setTimeInMillis(timestamp);
        int lastDay = calendar.get(Calendar.DAY_OF_WEEK);
        lastDay = lastDay == 1 ? 8 : lastDay;
        if (currDay < lastDay) {//
            return false;
        }
        if (currDay == lastDay) {
            if (currMls - timestamp > 2 * 24 * 60 * 60 * 1000) {
                return false;
            }
            return true;
        }
        if (currMls - timestamp > 7 * 24 * 60 * 60 * 1000) {
            return false;
        }
        return true;
    }

    /**
     * 当前系统是否低内存
     *
     * @return
     */
    public static boolean getIsLowMemory() {
        try {
            ActivityManager am = (ActivityManager) PicsApplication.getInstance().getSystemService(Context.ACTIVITY_SERVICE);
            MemoryInfo mi = new MemoryInfo();
            am.getMemoryInfo(mi);
            return mi.lowMemory;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    /**
     * 获取当前可用内存大小 availMem系统可用内存; threshold系统内存不足的阀值，即临界值;
     * lowMemory如果当前可用内存<=threshold，该值为真
     *
     * @return
     */
    public static String getAvailMemory() {// 获取android当前可用内存大小
        ActivityManager am = (ActivityManager) PicsApplication.getInstance().getSystemService(Context.ACTIVITY_SERVICE);
        MemoryInfo mi = new MemoryInfo();
        am.getMemoryInfo(mi);
        // mi.availMem; 当前系统的可用内存
        String availMem = Formatter.formatFileSize(PicsApplication.getInstance().getBaseContext(), mi.availMem);// 将获取的内存大小规格化
        String threshold = Formatter.formatFileSize(PicsApplication.getInstance().getBaseContext(), mi.threshold);// 将获取的系统内存不足的阀值，即临界值规格化
        // Integer.parseInt((availMem.replace("MB", "")));
        return mi.availMem + "  " + mi.threshold + "  " + mi.lowMemory;
    }

    /**
     * 得到设备序列号
     *
     * @return
     */
    public static String getSerialNumber() {
        String serial = null;
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class);
            serial = (String) get.invoke(c, "ro.serialno");
        } catch (Exception ignored) {
        }
        return serial;
    }

    /**
     * 获取CPU序列号
     *
     * @return CPU序列号(16位) 读取失败为"0000000000000000"
     */

    public static String getCPUSerial() {
        String str = "", strCPU = "", cpuAddress = "0000000000000000";
        try {
            // 读取CPU信息
            Process pp = Runtime.getRuntime().exec("cat /proc/cpuinfo");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            // 查找CPU序列号
            for (int i = 1; i < 100; i++) {
                str = input.readLine();
                if (str != null) {
                    // 查找到序列号所在行
                    if (str.indexOf("Serial") > -1) {
                        // 提取序列号
                        strCPU = str.substring(str.indexOf(":") + 1, str.length());
                        // 去空格
                        cpuAddress = strCPU.trim();
                        break;
                    }
                } else {
                    // 文件结尾
                    break;
                }
            }
        } catch (IOException ex) {
            // 赋予默认值
            ex.printStackTrace();
        }
        return cpuAddress;
    }


    /**
     * 得到是否是手机/平板设备
     *
     * @param mContext
     * @return true是手机 false是平板
     */

    public static boolean isPhone(Context mContext) {
        if (android.os.Build.VERSION.SDK_INT >= 11) { // honeycomb
            // test screen size, use reflection because isLayoutSizeAtLeast is
            // only available since 11
            try {
                Configuration con = mContext.getResources().getConfiguration();
                Method mIsLayoutSizeAtLeast = con.getClass().getMethod("isLayoutSizeAtLeast", int.class);
                Boolean r = (Boolean) mIsLayoutSizeAtLeast.invoke(con, 0x00000004); // Configuration.SCREENLAYOUT_SIZE_XLARGE
                if (r) {
                    // 平板
                } else {
                    // 手机
                }
                return !r;
            } catch (Exception x) {
                x.printStackTrace();
                return true;
            }
        }
        return true;
    }

    /**
     * 是否可以打电话
     *
     * @param mContext
     * @return
     */
    public static boolean isCanCallTelephony(Context mContext) {
        TelephonyManager telephony = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        int type = telephony.getPhoneType();
        boolean isCanCall = false;
        if (type == TelephonyManager.PHONE_TYPE_NONE) {
            isCanCall = false;
        } else {
            isCanCall = true;
        }
        return isCanCall;
    }

    /**
     * 判断是否为平板 大于6尺寸则为平板
     *
     * @return
     */
    public static boolean isPad(Context mContext) {
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        // 屏幕宽度
        float screenWidth = display.getWidth();
        // 屏幕高度
        float screenHeight = display.getHeight();
        DisplayMetrics dm = new DisplayMetrics();
        display.getMetrics(dm);
        double x = Math.pow(dm.widthPixels / dm.xdpi, 2);
        double y = Math.pow(dm.heightPixels / dm.ydpi, 2);
        // 屏幕尺寸
        double screenInches = Math.sqrt(x + y);
        // 大于6尺寸则为Pad
        if (screenInches >= 6.0) {
            return true;
        }
        return false;
    }

    private static String SAVE_DATA_PATH;

    public static void getSuitableCameraSize() {
        Camera camera = null;
        for (int i = 0; i < Camera.getNumberOfCameras(); i++) {
            android.hardware.Camera.CameraInfo info = new android.hardware.Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (null != camera) {
                camera.stopPreview();
                camera.release();
                camera = null;
            }
            camera = Camera.open(i);
            Camera.Parameters parameters = camera.getParameters();
            List<Camera.Size> supportedPreviewSizes = parameters.getSupportedPreviewSizes();
            for (Camera.Size previewSize : supportedPreviewSizes) {
            }
        }
        if (null != camera) {
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }

    /**
     * sim卡是否可用
     *
     * @return
     */

    public static boolean isCanUseSim() {
        try {
            TelephonyManager mgr = (TelephonyManager) PicsApplication.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
            return TelephonyManager.SIM_STATE_READY == mgr.getSimState();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取运营商类型
     *
     * @return
     */
    public static String getImsi() {
        TelephonyManager tm = (TelephonyManager) PicsApplication.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
        String IMSI = tm.getSubscriberId();
        if (IMSI == null) {
            return "";
        }
        return IMSI;
    }

    public static final int CHINA_MOBILE = 1;// 移动
    public static final int CHINA_UNICOM = 2;// 联通
    public static final int CHINA_TELECOM = 3;// 电信

    public static int selfOperater = -1;

    /**
     * 获取运营商类型
     *
     * @return
     */
    public static int getOperater() {
        if (selfOperater >= 0) {
            return selfOperater;
        }
        int type = 0;// 0未知1移动2联通3电信
        TelephonyManager tm = (TelephonyManager) PicsApplication.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
        String IMSI = tm.getSubscriberId();
        if (IMSI == null) {
            return type;
        }
        // IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。
        if (IMSI.startsWith("46000") || IMSI.startsWith("46002") || IMSI.startsWith("46007")) {
            type = CHINA_MOBILE;
        } else if (IMSI.startsWith("46001")) {
            type = CHINA_UNICOM;
        } else if (IMSI.startsWith("46003")) {
            type = CHINA_TELECOM;
        }
        selfOperater = type;
        return selfOperater;
    }

    /**
     * 是否24小时之内
     *
     * @param lastTime 记录中的时间
     * @return
     */
    public static boolean IsOutOneDay(long lastTime) {
        long lastDay = (int) (lastTime / (24 * 60 * 60 * 1000));
        long toDay = (System.currentTimeMillis() / (24 * 60 * 60 * 1000));
        if (lastDay == toDay) {
            return false;// 同一天
        } else {
            return true;// 不是同一天
        }
    }

    /**
     * 是否24*7小时之内
     *
     * @param lastTime 记录中的时间
     * @return
     */
    public static boolean IsOutSevenDay(long lastTime) {
        long lastDay = (int) (lastTime / (24 * 60 * 60 * 1000));
        long toDay = (System.currentTimeMillis() / (24 * 60 * 60 * 1000));
        if (toDay - lastDay > 7) {
            return true;// 超过7天
        } else {
            return false;// 未超过7天
        }
    }

    public static long getDays(long time) {
        return time / (24 * 60 * 60 * 1000);
    }

    /**
     * 数组倒序
     *
     * @param array
     * @return
     */
    public static float[] reverseOrder(float[] array) {
        for (int i = 0; i < array.length / 2; i++) {
            swap(array, i, array.length - 1 - i);
        }
        return array;
    }

    /**
     * 是否为中文、数字、字母
     *
     * @param s
     * @return
     */
    public static boolean isChineseOrLetterOrNum(String s) {
        if (s == null) {
            return false;
        }
        char[] ac = s.toCharArray();
        for (int i = 0; i < ac.length; i++) {
            if (CharType.DELIMITER == getCharType(ac[i])) {
                return false;
            }
            if (CharType.OTHER == getCharType(ac[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * 是否包含中文
     *
     * @param s
     * @return
     */
    public static boolean containChinese(String s) {
        if (s == null) {
            return false;
        }
        char[] ac = s.toCharArray();
        for (int i = 0; i < ac.length; i++) {
            if (CharType.CHINESE == getCharType(ac[i])) {
                return true;
            }
        }
        return false;
    }

    enum CharType {
        DELIMITER, // 非字母截止字符，例如，．）（　等等　（ 包含U0000-U0080）
        NUM, // 2字节数字１２３４
        LETTER, // gb2312中的，例如:ＡＢＣ，2字节字符同时包含 1字节能表示的 basic latin and latin-1
        OTHER, // 其他字符
        CHINESE;// 中文字
    }

    /**
     * 判断输入char类型变量的字符类型
     *
     * @param c char类型变量
     * @return CharType 字符类型
     */
    public static CharType getCharType(char c) {
        CharType ct = null;

        // 中文，编码区间0x4e00-0x9fbb
        if ((c >= 0x4e00) && (c <= 0x9fbb)) {
            ct = CharType.CHINESE;
        } else if ((c >= 0xff00) && (c <= 0xffef)) {
            // Halfwidth and Fullwidth Forms， 编码区间0xff00-0xffef
            // 2字节英文字
            if (((c >= 0xff21) && (c <= 0xff3a)) || ((c >= 0xff41) && (c <= 0xff5a))) {
                ct = CharType.LETTER;
            } else if ((c >= 0xff10) && (c <= 0xff19)) {
                // 2字节数字
                ct = CharType.NUM;
            } else {
                // 其他字符，可以认为是标点符号
                ct = CharType.DELIMITER;
            }
        } else if ((c >= 0x0021) && (c <= 0x007e)) {
            // basic latin，编码区间 0000-007f
            // 1字节数字
            if ((c >= 0x0030) && (c <= 0x0039)) {
                ct = CharType.NUM;
            } // 1字节字符
            else if (((c >= 0x0041) && (c <= 0x005a)) || ((c >= 0x0061) && (c <= 0x007a))) {
                ct = CharType.LETTER;
            }
            // 其他字符，可以认为是标点符号
            else
                ct = CharType.DELIMITER;
        }

        // latin-1，编码区间0080-00ff
        else if ((c >= 0x00a1) && (c <= 0x00ff)) {
            if ((c >= 0x00c0) && (c <= 0x00ff)) {
                ct = CharType.LETTER;
            } else
                ct = CharType.DELIMITER;
        } else
            ct = CharType.OTHER;

        return ct;
    }

    private static void swap(float[] array, int i, int j) {
        float tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }

    /**
     * * 转为字符串 * * @param obj * @return 为null时返回空字符串
     */
    public static String objectToString(Object obj) {
        String str = "";
        try {
            str = (String) obj;
            if (str == null) {
                str = "";
            }
        } catch (ClassCastException ce) {
            try {
                str = String.valueOf(obj);
            } catch (Exception e) {
                str = "";
            }
        }
        return str.trim();
    }

    /**
     * 数组由小到大
     *
     * @param srcArray
     * @return
     */
    public static int[] order(int[] srcArray) {
        int[] dist = new int[srcArray.length];
        System.arraycopy(srcArray, 0, dist, 0, srcArray.length);
        for (int i = 0; i < dist.length; i++) {
            int lowerIndex = i;
            // 找出最小的一个索引
            for (int j = i + 1; j < dist.length; j++) {
                if (dist[j] < dist[lowerIndex]) {
                    lowerIndex = j;
                }
            }
            // 交换
            int temp = dist[i];
            dist[i] = dist[lowerIndex];
            dist[lowerIndex] = temp;
        }
        return dist;
    }

    public static String[] splitStr(String str, String c) {
        ArrayList<String> al = new ArrayList<String>();
        while (true) {
            int nPos = str.indexOf(c);
            if (nPos == -1) {
                nPos = str.length();
            }
            String s = str.substring(0, nPos);
            al.add(s);
            if (nPos == str.length()) {
                break;
            }
            str = str.substring(nPos + c.length());
            if (str.equals("")) {
                al.add("");
                break;
            }
        }
        String[] as = new String[al.size()];
        al.toArray(as);
        return as;
    }

    public static boolean inArray(int[] an, int n) {
        if (an == null) {
            return false;
        }
        for (int i = 0; i < an.length; i++) {
            if (an[i] == n) {
                return true;
            }
        }
        return false;
    }

    /**
     * 往object数组里添加一个元素
     */
    public static Object[] addObjectToArray(Object[] srcArray, Object val) {
        Object[] dist = new Object[srcArray.length + 1];
        System.arraycopy(srcArray, 0, dist, 0, srcArray.length);
        dist[srcArray.length] = val;
        return dist;
    }

    /**
     * 往int数组里添加一个元素
     */
    public static int[] addIntToArray(int[] srcArray, int val) {
        int[] dist = new int[srcArray.length + 1];
        System.arraycopy(srcArray, 0, dist, 0, srcArray.length);
        dist[srcArray.length] = val;
        return dist;
    }

    /**
     * 往int里面插入一个元素
     */
    public static int[] insertIntToArray(int[] srcArray, int index, int val) {
        int[] dist = new int[srcArray.length + 1];
        System.arraycopy(srcArray, 0, dist, 0, index);
        dist[index] = val;
        System.arraycopy(srcArray, index, dist, index + 1, srcArray.length - index);
        return dist;
    }

    /**
     * 从int里面删除一个元素
     */
    public static int[] delIntFromArray(int[] srcArray, int index) {
        if (srcArray.length < 0) {
            return null;
        }
        int[] dist = new int[srcArray.length - 1];
        System.arraycopy(srcArray, 0, dist, 0, index);
        System.arraycopy(srcArray, index + 1, dist, index, srcArray.length - index - 1);
        return dist;
    }

    public static int[] delIntFromArray1(int[] srcArray, int val) {
        if (srcArray.length < 0) {
            return null;
        }
        for (int i = 0; i < srcArray.length; i++) {
            if (val == srcArray[i]) {
                return delIntFromArray(srcArray, i);
            }
        }
        return null;
    }

    public static String[] addStringToArray(String[] srcArray, String s) {
        String[] dist = new String[srcArray.length + 1];
        System.arraycopy(srcArray, 0, dist, 0, srcArray.length);
        dist[srcArray.length] = s;
        return dist;
    }

    public static boolean inRect(int PointX, int PointY, int RectX, int RectY, int RectW, int RectH) {
        return (PointX > RectX && PointX < RectX + RectW && PointY > RectY && PointY < RectY + RectH);
    }

    /**
     * CPU利用率
     *
     * @return
     */
    public static float readUsage() {
        try {
            RandomAccessFile reader = new RandomAccessFile("/proc/stat", "r");

            String load = reader.readLine();

            String[] toks = load.split(" ");

            long idle1 = Long.parseLong(toks[5]);

            long cpu1 = Long.parseLong(toks[2]) + Long.parseLong(toks[3]) + Long.parseLong(toks[4]) + Long.parseLong(toks[6]) + Long.parseLong(toks[7]) + Long.parseLong(toks[8]);

            try {
                Thread.sleep(360);

            } catch (Exception e) {
            }

            reader.seek(0);

            load = reader.readLine();

            reader.close();

            toks = load.split(" ");

            long idle2 = Long.parseLong(toks[5]);

            long cpu2 = Long.parseLong(toks[2]) + Long.parseLong(toks[3]) + Long.parseLong(toks[4]) + Long.parseLong(toks[6]) + Long.parseLong(toks[7]) + Long.parseLong(toks[8]);

            return (int) (100 * (cpu2 - cpu1) / ((cpu2 + idle2) - (cpu1 + idle1)));

        } catch (IOException ex) {
            ex.printStackTrace();

        }

        return 0;
    }

    /**
     * 将一个时间戳转换成提示性时间字符串，如刚刚，1秒前
     *
     * @param timeStamp
     * @return
     */
    public static String convertTimeToFormat(long timeStamp) {
        long curTime = System.currentTimeMillis() / (long) 1000;
        long time = curTime - timeStamp;

        if (time < 60 && time >= 0) {
            return "刚刚";
        } else if (time >= 60 && time < 3600) {
            return time / 60 + "分钟前";
        } else if (time >= 3600 && time < 3600 * 24) {
            return time / 3600 + "小时前";
        } else if (time >= 3600 * 24 && time < 3600 * 24 * 5) {
            return time / 3600 / 24 + "天前";
        } else {
            return formattingTime(timeStamp);
        }
    }

    public static String getGameTimeAsString(int time, boolean bHundreds) {
        StringBuffer result_time = new StringBuffer();
        String symbol = ":";
        if (time <= 0) {
            return "00:00";
        }
        String minutes = getPrettyNumber(time / 60);
        String seconds = getPrettyNumber(time % 60);
        String hundreds = "";
        if (bHundreds) {
            hundreds = getPrettyNumber((time / 10) % 100);
        }

        result_time.append(minutes);
        result_time.append(symbol);
        result_time.append(seconds);
        if (bHundreds) {
            result_time.append(symbol);
            result_time.append(hundreds);
        }

        return result_time.toString();
    }

    public static int[] Millis2Time(long mills)// unix时间戳，毫秒值
    {
        int an[] = new int[2];
        if (mills < 1)
            return an;
        SimpleDateFormat f1 = new SimpleDateFormat("HH");
        f1.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        SimpleDateFormat f2 = new SimpleDateFormat("mm");
        f2.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        // SimpleDateFormat formatter = new
        // SimpleDateFormat("yyyy-MM-dd  HH:mm");
        Date curDate = new Date(mills);// 获取当前时间
        String time = "";
        time = f1.format(curDate);
        an[0] = Integer.parseInt(time);
        time = f2.format(curDate);
        an[1] = Integer.parseInt(time);
        return an;
    }

    /**
     * 格式化数字转换为时间yyyy-MM-dd HH:mm
     *
     * @param mills
     * @return
     */
    public static String formattingTime(long mills) {
        if (mills < 1) {
            return "";
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd  HH:mm");
        Date curDate = new Date(mills);// 获取当前时间
        String time = formatter.format(curDate);
        return time;
    }

    /**
     * 将短时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
     *
     * @param strDate
     * @return
     */
    public static Date parseDate(String strDate) {
        if (strDate == null || "".equals(strDate)) {
            return new Date();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    // G 年代标志符
    // y 年
    // M 月
    // d 日
    // h 时 在上午或下午 (1~12)
    // H 时 在一天中 (0~23)
    // m 分
    // s 秒
    // S 毫秒
    // E 星期
    // D 一年中的第几天
    // F 一月中第几个星期几
    // w 一年中第几个星期
    // W 一月中第几个星期
    // a 上午 / 下午 标记符
    // k 时 在一天中 (1~24//
    // K 时 在上午或下午 (0~11)
    // z 时区

    /**
     * 按照formater的格式，格式化date
     *
     * @param date
     * @param formater
     * @return
     */
    public static String formatDate(Date date, String formater) {
        SimpleDateFormat formatter = new SimpleDateFormat(formater);
        return formatter.format(date);
    }

    private static String getPrettyNumber(int n) {
        if (n < 0) {
            n = -n;
        }
        if (n < 10) {
            return "0" + n;
        }
        return String.valueOf(n);
    }

    public static void writeFile(String filePath, byte[] abData) {
        InputStream is = new ByteArrayInputStream(abData);
        writeFile(filePath, is);
    }

    public static void writeFile(String filePath, InputStream is) {
        File file = new File(filePath);
        try {
            int nPos = filePath.lastIndexOf('/');
            String sDir = filePath.substring(0, nPos);
            File dir = new File(sDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            OutputStream outstream = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = is.read(buffer)) != -1) {
                outstream.write(buffer, 0, len);
            }
            outstream.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 写文本文件
     *
     * @param filePath
     * @param content
     */
    public static void writeTxt(String filePath, String content) {

        // 保存文件
        File file = new File(filePath);
        try {
            OutputStream outstream = new FileOutputStream(file);
            OutputStreamWriter out = new OutputStreamWriter(outstream);
            out.write(content);
            out.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读文本文件
     *
     * @param path
     * @return
     */
    public static String readTxt(String path) {
        String content = "";
        // 打开文件
        File file = new File(path);
        // 如果path是传递过来的参数，可以做一个非目录的判断
        if (file.isDirectory()) {
            // Toast.makeText(EasyNote.this, "没有指定文本文件！", 1000).show();
        } else {
            try {
                InputStream instream = new FileInputStream(file);
                if (instream != null) {
                    InputStreamReader inputreader = new InputStreamReader(instream);
                    BufferedReader buffreader = new BufferedReader(inputreader);
                    String line;
                    // 分行读取
                    while ((line = buffreader.readLine()) != null) {
                        content += line + "\n";
                    }
                    instream.close();
                }
            } catch (java.io.FileNotFoundException e) {
                // Toast.makeText(EasyNote.this, "文件不存在",
                // Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        content = content.trim();
        return content;
    }

    /**
     * CPU信息(主频)
     *
     * @return
     */
    public static float getCpuInfo() {
        String str1 = "/proc/cpuinfo";
        String str2 = "";
        String cpuInfo = "";
        String[] arrayOfString;
        try {
            FileReader fr = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
            str2 = localBufferedReader.readLine();
            // arrayOfString = str2.split("\\s+");
            // for (int i = 2; i < arrayOfString.length; i++) {
            // cpuInfo[0] = cpuInfo[0] + arrayOfString[i] + " ";
            // }
            str2 = localBufferedReader.readLine();
            arrayOfString = str2.split("\\s+");
            cpuInfo += arrayOfString[2];
            localBufferedReader.close();
        } catch (IOException e) {
        }
        float f = Float.parseFloat(cpuInfo);
        return f;
    }

    public static String MaxCpuFreq = null;

    // 获取CPU最大频率（单位KHZ）
    public static String getMaxCpuFreq() {
        if (MaxCpuFreq != null) {
            return MaxCpuFreq;
        }
        String result = "";
        ProcessBuilder cmd;
        try {
            String[] args = {"/system/bin/cat", "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq"};
            cmd = new ProcessBuilder(args);
            Process process = cmd.start();
            InputStream in = process.getInputStream();
            byte[] re = new byte[24];
            while (in.read(re) != -1) {
                result = result + new String(re);
            }
            in.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            result = "N/A";
        }
        MaxCpuFreq = result.trim();
        return MaxCpuFreq;
    }

    // 获取CPU最小频率（单位KHZ）
    public static String getMinCpuFreq() {
        String result = "";
        ProcessBuilder cmd;
        try {
            String[] args = {"/system/bin/cat", "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_min_freq"};
            cmd = new ProcessBuilder(args);
            Process process = cmd.start();
            InputStream in = process.getInputStream();
            byte[] re = new byte[24];
            while (in.read(re) != -1) {
                result = result + new String(re);
            }
            in.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            result = "N/A";
        }
        return result.trim();
    }

    // 实时获取CPU当前频率（单位KHZ）
    public static String getCurCpuFreq() {
        String result = "N/A";
        try {
            FileReader fr = new FileReader("/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq");
            BufferedReader br = new BufferedReader(fr);
            String text = br.readLine();
            result = text.trim();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    // public static int dip2px(Context context, float dipValue) {
    // final float scale =
    // context.getResources().getDisplayMetrics().densityDpi;
    // return (int) (dipValue * (scale / 160) + 0.5f);
    // }
    //
    // public static int px2dp(Context context, float pxValue) {
    // final float scale =
    // context.getResources().getDisplayMetrics().densityDpi;
    // return (int) ((pxValue * 160) / scale + 0.5f);
    // }

    public static void getHourMinute(int[] data) {
        Time t = new Time(); // or Time t=new Time("GMT+8"); 加上Time Zone资料。

        t.setToNow(); // 取得系统时间

        data[0] = t.hour; // 0-23
        data[1] = t.minute;
    }

    public static Bitmap loadBitmap(Context context, int drawableId) {
        Bitmap bitmap = null;
        BitmapFactory.Options opt = new BitmapFactory.Options();
        TypedValue value = new TypedValue();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inJustDecodeBounds = false;
        opt.inDither = false;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        opt.inTargetDensity = value.density;
        InputStream is = context.getResources().openRawResource(drawableId);
        try {
            bitmap = BitmapFactory.decodeStream(is, null, opt);
        } finally {
            try {
                is.close();
                is = null;
            } catch (IOException e) {
            }
        }
        return bitmap;
    }

    public static Bitmap loadBitmap(Context context, String resName) {
        try {
            resName = resName.toLowerCase();
            String fn = resName;
            int i = fn.lastIndexOf('/');
            if (i >= 0) {
                fn = fn.substring(i + 1);
            }
            int j = fn.indexOf(".png");
            if (j > 0) {
                fn = fn.substring(0, j);
            }
            return decodeResource(context.getResources(), context.getResources().getIdentifier(fn, "drawable", context.getPackageName()));
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }

    }

    private static Bitmap decodeResource(Resources resources, int id) {
        TypedValue value = new TypedValue();
        resources.openRawResource(id, value);
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inTargetDensity = value.density;
        return BitmapFactory.decodeResource(resources, id, opts);
    }

    public static Bitmap scaleImage(Bitmap bitmap, int dst_w, int dst_h) {
        int src_w = bitmap.getWidth();
        int src_h = bitmap.getHeight();
        if (src_w == dst_w && src_h == dst_h) {
            return bitmap;
        }
        float scale_w = ((float) dst_w) / src_w;
        float scale_h = ((float) dst_h) / src_h;
        Matrix matrix = new Matrix();
        matrix.postScale(scale_w, scale_h);
        Bitmap dstbmp = Bitmap.createBitmap(bitmap, 0, 0, src_w, src_h, matrix, true);
        bitmap.recycle();
        return dstbmp;
    }

    public static Bitmap scaleImage(Bitmap bitmap, float scale, boolean recycle) {
        if (scale == 1.0f || getIsLowMemory()) {
            System.gc();
            return bitmap;
        }
        Bitmap dstbmp = bitmap;
        try {
            int src_w = bitmap.getWidth();
            int src_h = bitmap.getHeight();

            Matrix matrix = new Matrix();
            matrix.postScale(scale, scale);
            dstbmp = Bitmap.createBitmap(bitmap, 0, 0, src_w, src_h, matrix, true);
            if (recycle) {
                bitmap.recycle();
            }
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
        return dstbmp;
    }

    /**
     * 圆角图片
     */
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {
        if (getIsLowMemory()) {
            System.gc();
            return bitmap;
        }
        Bitmap output = bitmap;
        try {
            output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
            Canvas canvas = new Canvas(output);
            final int color = 0xff424242;
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            final RectF rectF = new RectF(rect);
            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
            paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
        // bitmap.recycle();
        return output;
    }

    /**
     * 得到手机型号
     */
    public static String getModel() {
        return Build.MODEL;
    }

    /**
     * 得到系统版本号
     */
    public static String getSDK_VERSION() {
        return android.os.Build.VERSION.RELEASE;
    }

    public static int mnNetType = 0;// 联网类型

    public static final int NET_WIFI = 1;
    public static final int NET_2G = 2;
    public static final int NET_3G = 3;

    public static int getConnectionType() {
        if (mnNetType > 0) {
            return mnNetType;
        }
        ConnectivityManager manager = (ConnectivityManager) PicsApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info == null) {
            mnNetType = 0;
            return mnNetType;
        }
        int netType = info.getType();
        int netSubtype = info.getSubtype();
        if (netType == ConnectivityManager.TYPE_WIFI) {
            mnNetType = NET_WIFI;
        } else if (netSubtype == TelephonyManager.NETWORK_TYPE_UMTS || netSubtype == TelephonyManager.NETWORK_TYPE_HSDPA || netSubtype == TelephonyManager.NETWORK_TYPE_EVDO_A) {
            // 联通UMTS或HSDPA 电信EVDO
            mnNetType = NET_3G;
        } else {
            mnNetType = NET_2G;
        }
        return mnNetType;
    }

    /**
     * 得到联网方式
     */
    public static String getNetType() {
        try {
            ConnectivityManager cm = (ConnectivityManager) PicsApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = cm.getActiveNetworkInfo();
            if (info == null) {
                return "";
            }
            String typeName = info.getTypeName(); // WIFI/MOBILE
            int type = info.getType();
            if (type == ConnectivityManager.TYPE_WIFI) {

            } else {
                typeName = info.getExtraInfo(); // 3gnet/3gwap/uninet/uniwap/cmnet/cmwap/ctnet/ctwap
            }
            if (typeName == null) {
                return "";
            }
            return typeName;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 得到运营商的名称
     */
    public static String getNetName() {
        try {
            ConnectivityManager cm = (ConnectivityManager) PicsApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = cm.getActiveNetworkInfo();
            String netName = null;
            int type = info.getType();
            if (type == ConnectivityManager.TYPE_WIFI) {

                netName = info.getTypeName(); // WIFI/MOBILE
            } else {
                TelephonyManager tm = (TelephonyManager) PicsApplication.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
                netName = tm.getNetworkOperatorName();
            }
            if (netName == null) {
                return "";
            }
            return netName;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 得到手机号
     */
    public static String getNumber() {
        try {
            String number = null;
            TelephonyManager tm = (TelephonyManager) PicsApplication.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
            number = tm.getLine1Number();
            if (number == null) {
                return "";
            }
            return number;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 得到屏幕宽高
     */
    public static Point getDisplaySize(final Display display) {
        final Point point = new Point();
        try {
            display.getSize(point);
        } catch (java.lang.NoSuchMethodError ignore) { // Older device
            point.x = display.getWidth();
            point.y = display.getHeight();
        }
        return point;
    }

    public static PackageInfo getPkgInfoByName(String name) {
        Context context = PicsApplication.getInstance();
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(name, PackageManager.GET_ACTIVITIES);
        } catch (Exception e) {
        }

        return packageInfo;
    }

    /**
     * 得到版本号
     *
     * @return
     */
    public static String getVersionName() {
        try {
            String packageName = PicsApplication.getInstance().getPackageName();
            return PicsApplication.getInstance().getPackageManager().getPackageInfo(packageName, PackageManager.GET_CONFIGURATIONS).versionName;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 得到版本编号
     *
     * @return
     */
    public static int getVersionCodeNoChannel() {
        try {
            String packageName = PicsApplication.getInstance().getPackageName();
            return PicsApplication.getInstance().getPackageManager().getPackageInfo(packageName, PackageManager.GET_CONFIGURATIONS).versionCode;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 获取手机IMEI
     *
     * @return
     */
    public static String getIMEI() {
        String mImei = "NULL";
        try {
            mImei = ((TelephonyManager) PicsApplication.getInstance()
                    .getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
        } catch (Exception e) {
            System.out.println("获取IMEI码失败");
            mImei = "NULL";
        }
        return mImei;
    }

    private static String macAddr;

    /**
     * 得到Mac地址
     *
     * @return
     */
    public static String getMacAddr() {
        if (TextUtils.isEmpty(macAddr)) {
            macAddr = "02:00:00:00:00:00";
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                macAddr = getMacAddrOnMarshmallow();
            } else {
                try {
                    WifiManager wifi;
                    wifi = (WifiManager) PicsApplication.getInstance().getSystemService(Context.WIFI_SERVICE);
                    WifiInfo info = wifi.getConnectionInfo();
                    macAddr = info.getMacAddress();
                } catch (Exception e) {
                }
            }
        }
        return macAddr;
    }

    /**
     * 在android6.0上获取mac地址
     *
     * @return
     */
    public static String getMacAddrOnMarshmallow() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    String hex = Integer.toHexString(b & 0xFF);
                    if (hex.length() == 1) {
                        hex = '0' + hex;
                    }
                    res1.append(hex + ":");
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
        }
        return "02:00:00:00:00:00";
    }

    private static String SAVE_PATH;

    public static String getSavePath() {
        if (SAVE_PATH != null) {
            return SAVE_PATH;
        }
        if (!Environment.getExternalStorageDirectory().canWrite()) {
            SAVE_PATH = PicsApplication.getInstance().getFilesDir().getAbsolutePath() + File.separator;
            // SAVE_PATH = "";
            return SAVE_PATH;
        }
        String sSavePath = Environment.getExternalStorageDirectory() + File.separator + Const.APP_NAME + File.separator;
        File dir = new File(sSavePath);
        if (dir.exists()) {
            SAVE_PATH = sSavePath;
            return SAVE_PATH;
        }

        boolean b = dir.mkdirs();
        if (b) {
            SAVE_PATH = sSavePath;
            return SAVE_PATH;
        }
        SAVE_PATH = PicsApplication.getInstance().getFilesDir().getAbsolutePath() + "/";
        return SAVE_PATH;
    }

    /**
     * 是否当前App正在运行
     *
     * @return
     */
    public static boolean isSelfAppRunning(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        // 获取正在运行的应用
        List<RunningAppProcessInfo> run = am.getRunningAppProcesses();
        int i = 0;
        for (RunningAppProcessInfo ra : run) {
            // Pub.LOG("i=" + i);
            // i++;
            // 这里主要是过滤系统的应用和电话应用
            if (ra.processName.equals("system") || ra.processName.equals("com.android.phone")) {
                continue;
            }
            if (ra.processName.equals("cn.bluestrom.client." + Const.APP_NAME)) {
                return true;
            }
        }
        return false;
    }

    private static ProgressDialog mProgressDialog;

    /**
     * 显示Loading
     *
     * @param context
     * @param sMsg
     */
    public static void showProgressDialog(Context context, String sMsg) {
        try {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
            mProgressDialog = new ProgressDialog(PicsApplication.getInstance());
            mProgressDialog.setMessage(sMsg);
            mProgressDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * loading是否还在显示中
     *
     * @return
     */
    public static boolean isProgressDialogShowing() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            return true;
        } else {
            return false;
        }
    }

    public static int modifyColorBrightness(int color, float factor) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= factor;
        return Color.HSVToColor(hsv);
    }
}
