package com.xinye.core.utils.app;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * 应用程序包工具类.
 *
 * @author wangheng
 */
public class PackageUtils {
    /**
     * 得到Application的指定的KEY的MetaData信息
     *
     * @param metaDataKey
     * @return
     */
    public static String getApplicationMetadata(Context context, String metaDataKey) {
        ApplicationInfo info = null;
        try {
            PackageManager pm = context.getPackageManager();

            info = pm.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);

            return String.valueOf(info.metaData.get(metaDataKey));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * getPackageName:得到应用包名. <br/>
     *
     * @return
     * @author wangheng
     */
    public static String getPackageName(Context context) {
        return context.getPackageName();
    }

    /**
     * getVersionName:得到版本名称. <br/>
     *
     * @return
     * @author wangheng
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "1.0";
    }

    /**
     * getVersionCode:得到版本号. <br/>
     *
     * @return
     * @author wangheng
     */
    public static int getVersionCode(Context context) {

        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            int version = info.versionCode;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

    /**
     * getMetaInfChannel:得到APK下的META-INF目录下的渠道号. <br/>
     *
     * @param context context
     * @return 渠道号
     * @author wangheng
     */
    public static String getMetaInfChannel(Context context) {
        ApplicationInfo appinfo = context.getApplicationInfo();
        String sourceDir = appinfo.sourceDir;
        String ret = "";
        ZipFile zipfile = null;
        try {
            zipfile = new ZipFile(sourceDir);
            Enumeration<?> entries = zipfile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = ((ZipEntry) entries.nextElement());
                String entryName = entry.getName();
                //如果想修改此标示，直接编辑package.py即可
                if (entryName.startsWith("META-INF/reshow")) {
                    ret = entryName;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (zipfile != null) {
                try {
                    zipfile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        String[] split = ret.split("_");
        if (split != null && split.length >= 2) {
            return ret.substring(split[0].length() + 1);
        } else {
            return "";
        }
    }

    public static boolean isAppProcess(Context context) {
        try {
            int pid = android.os.Process.myPid();
            String packageName = PackageUtils.getPackageName(context);
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> list = am.getRunningAppProcesses();
            if (list != null) {
                for (ActivityManager.RunningAppProcessInfo info : list) {
                    if (pid == info.pid && packageName.equals(info.processName)) {
                        return true;
                    }
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
