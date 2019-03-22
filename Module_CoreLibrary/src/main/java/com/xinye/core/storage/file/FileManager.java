package com.xinye.core.storage.file;

import android.os.Environment;
import com.xinye.core.App;
import com.xinye.core.R;

import java.io.File;


/**
 * 文件管理器
 *
 * @author wangheng
 */
public class FileManager {

    // SD卡文件根目录
    private static final String APP_DIR_NAME = App.INSTANCE.getString(R.string.app_name);
    //上传图片文件夹名
    private static final String UPLOAD_SAVE_DIR_NAME = "image-upload";
    //图片文件夹名
    private static final String IMAGE_DIR_NAME = "image";
    // apk保存目录
    private static final String APK_SAVE_DIR_NAME = "apk";

    private File mAppDir;

    private FileManager() {

    }

    /**
     * 得到SPUtils的单例
     *
     * @return 单例
     */
    public static FileManager getInstance() {
        return Singleton.INSTANCE;
    }

    public void init() {
        initIfNeed();
    }

    /**
     * 初始化磁盘目录
     */
    private void initIfNeed() {

        if (mAppDir != null && mAppDir.exists()) {
            return;
        }

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {// 如果存在SD卡
            String dir = Environment.getExternalStorageDirectory()
                    + File.separator + FileManager.APP_DIR_NAME;

            // 初始化根目录
            mAppDir = new File(dir);
            if (!mAppDir.exists()) {
                mAppDir.mkdir();
                if(!mAppDir.exists()) {
                    mAppDir.mkdirs();
                }
            }
        }
    }

    public String getApkSaveDir() {
        return getDirPath(APK_SAVE_DIR_NAME);
    }

    public String getImageSaveDir() {
        return getDirPath(IMAGE_DIR_NAME);
    }

    public String getImageUploadSavePath() {
        return getDirPath(UPLOAD_SAVE_DIR_NAME);
    }


    private String getDirPath(String dirName) {
        initIfNeed();

        if (mAppDir == null || !mAppDir.exists()) {
            return null;
        }


        File dir = new File(mAppDir.getAbsolutePath() + File.separator + dirName);
        if (!dir.exists()) {
            dir.mkdir();
            if(!dir.exists()){
                dir.mkdirs();
            }
        }

        return dir.getAbsolutePath();
    }

    /**
     * 这个类存在的唯一意义就是为了创建FileManager的单例
     *
     * @author wangheng
     */
    private static final class Singleton {
        private static final FileManager INSTANCE = new FileManager();
    }
}
