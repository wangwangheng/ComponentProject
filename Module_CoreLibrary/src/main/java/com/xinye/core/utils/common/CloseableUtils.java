package com.xinye.core.utils.common;

import android.database.Cursor;
import com.xinye.core.log.Logger;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 资源关闭工具类
 *
 * @author wangheng
 */
public class CloseableUtils {

    private static final String TAG = "CloseableUtils";

    public static void close(Closeable t){
        if (t != null) {
            try {
                t.close();
            } catch (IOException e) {
                Logger.e(TAG,"close exception:" + e);
            }
        }
    }

    public static void close(InputStream t) {
        if (t != null) {
            try {
                t.close();
            } catch (IOException e) {
                Logger.e(TAG,"close exception:" + e);
            }
        }
    }

    public static void close(Socket t) {
        if (t != null) {
            try {
                t.close();
            } catch (IOException e) {
                Logger.e(TAG,"close exception:" + e);
            }
        }
    }

    public static void close(Cursor t) {
        if (t != null) {
            t.close();
        }
    }

    public static void close(RandomAccessFile t) {
        if (t != null) {
            try {
                t.close();
            } catch (IOException e) {
                Logger.e(TAG,"close exception:" + e);
            }
        }
    }

    public static void close(ServerSocket t) {
        if (t != null) {
            try {
                t.close();
            } catch (IOException e) {
                Logger.e(TAG,"close exception:" + e);
            }
        }
    }

    public static void close(Reader t) {
        if (t != null) {
            try {
                t.close();
            } catch (IOException e) {
                Logger.e(TAG,"close exception:" + e);
            }
        }
    }

    public static void close(Writer t) {
        if (t != null) {
            try {
                t.close();
            } catch (IOException e) {
                Logger.e(TAG,"close exception:" + e);
            }
        }
    }

    public static void close(OutputStream t) {
        if (t != null) {
            try {
                t.close();
            } catch (IOException e) {
                Logger.e(TAG,"close exception:" + e);
            }
        }
    }
}
