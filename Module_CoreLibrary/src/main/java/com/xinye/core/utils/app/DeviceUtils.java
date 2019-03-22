package com.xinye.core.utils.app;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import com.xinye.core.App;
import com.xinye.core.utils.common.CloseableUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.UUID;


/**
 * 设备信息工具类
 *
 * @author wangheng
 */
public class DeviceUtils {


    /**
     * 得到手机IMEI，调用这个方法必须得有READ_PHONE_STATE权限
     */
    public static String getImei(Context context) {
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (tm != null) {
                @SuppressLint("MissingPermission")
                String deviceId = tm.getDeviceId();
                if (!TextUtils.isEmpty(deviceId))
                    return deviceId;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // return getMac(context);
        return "";
    }

    /**
     * 得到Mac地址
     */
    public static String getMac(Context context) {
        String mac = null;
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (wifi != null) {
            WifiInfo info = wifi.getConnectionInfo();
            if (info != null) {
                mac = info.getMacAddress();
                return mac;
            }
        }
        return UUID.randomUUID().toString();

    }

    /**
     * 获取androidid
     */
    public static String getAndroidId(Context context) {
        String androidId = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
        if (androidId != null)
            return androidId;
        return UUID.randomUUID().toString();
    }

    /**
     * 获取CPU序列号
     *
     * @return CPU序列号(16位) 读取失败为"0000000000000000"
     */
    public static String getCPUSerial() {
        String str = "", strCPU = "", cpuAddress = "0000000000000000";
        InputStreamReader ir = null;
        LineNumberReader input = null;
        try {
            // 读取CPU信息
            Process pp = Runtime.getRuntime().exec("cat /proc/cpuinfo");
            ir = new InputStreamReader(pp.getInputStream(), "UTF-8");
            input = new LineNumberReader(ir);
            // 查找CPU序列号
            for (int i = 1; i < 100; i++) {
                str = input.readLine();
                if (str != null) {
                    // 查找到序列号所在行
                    if (str.contains("Serial")) {
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
        } finally {
            CloseableUtils.close(input);
            CloseableUtils.close(ir);
        }
        return cpuAddress;
    }

    /**
     * 获取设备型号
     *
     * @return 型号
     */
    public static String getModel() {
        return Build.MODEL;
    }

    /**
     * 获取设备品牌
     *
     * @return 设备品牌
     */
    public static String getBrand() {
        return Build.BRAND;
    }

    /**
     * 获取OS版本号
     *
     * @return 操作系统版本号
     */
    public static String getOSVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * getSystemAvaialbeMemorySize:获得系统可用内存信息. <br/>
     *
     * @param context context
     */
    public static long getSystemAvaialbeMemory(Context context) {
        // 获得MemoryInfo对象
        MemoryInfo memoryInfo = new MemoryInfo();
        // 获得系统可用内存，保存在MemoryInfo对象上
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(memoryInfo);

        return memoryInfo.availMem;
    }

    /**
     * 判断当前版本是否兼容目标版本的方法
     *
     * @param versionCode  版本号
     * @return 是否兼容
     */
    public static boolean isMethodsCompat(int versionCode) {
        int currentVersion = Build.VERSION.SDK_INT;
        return currentVersion >= versionCode;
    }

    /**
     * getScreenWidth:得到屏幕宽度(像素点数). <br/>
     *
     * @return 屏幕宽度
     */
    public static int getScreenWidth() {
        return getDisplayMetrics().widthPixels;
    }

    /**
     * getScreenHeight:得到屏幕高度(像素点数). <br/>
     *
     * @return 屏幕高度
     */
    public static int getScreenHeight() {
        return getDisplayMetrics().heightPixels;
    }

    /**
     * getDensity:得到像素密度 <br/>
     */
    public static float getDensity() {
        return getDisplayMetrics().density;
    }

    /**
     * getDensityDpi:得到每英寸的像素点数<br/>
     */
    public static int getDensityDpi() {
        return getDisplayMetrics().densityDpi;
    }

    /**
     * getDisplayMetrics:返回DisplayMetrics对象，以方便得到屏幕相关信息. <br/>
     */
    private static DisplayMetrics getDisplayMetrics() {
        DisplayMetrics dm = new DisplayMetrics();
        try {
            WindowManager manager = (WindowManager) App.INSTANCE.getContext().getSystemService(Context.WINDOW_SERVICE);
            Display display = manager.getDefaultDisplay();
            if (display != null) {
                display.getMetrics(dm);
            } else {
                dm.setToDefaults();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dm;
    }

    /*
    *  返回注册的网络运营商的名字  例如：中国移动
    * */
    public static String getNetworkOperatorName(Context context) {
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (tm != null) {
                String netOperator = tm.getNetworkOperatorName();
                if (!TextUtils.isEmpty(netOperator))
                    return netOperator;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取SIM卡运营商的名字 例如： CMCC
     *
     * @param context context
     */
    public static String getSimOperatorName(Context context) {
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (tm != null) {
                String simOperator = tm.getSimOperatorName();
                if (!TextUtils.isEmpty(simOperator))
                    return simOperator;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 获取当前系统版本号
     */
    public static int getAndroidSDKINT() {
        return Build.VERSION.SDK_INT;
    }


}
