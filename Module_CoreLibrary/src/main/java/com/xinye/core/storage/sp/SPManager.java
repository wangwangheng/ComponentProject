package com.xinye.core.storage.sp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import com.xinye.core.App;

import java.util.Set;

/**
 * SharedPreferences工具类
 * <p>
 * 如果为了效率考虑，可以把commit()方法修改为apply()方法：
 * <p>
 * apply()方法会把修改的值放到内存中，然后在合适的时机同步到xml文件中
 * commit()方法会直接修改xml文件
 * <p>
 * apply()方法执行不会得到执行结果，而commit()方法可以得到执行结果；apply()方法更高效
 *
 * @author wangheng
 */
public class SPManager {

    /**
     * SharedPreferences的实例 *
     */
    private SharedPreferences mSP = null;

    private String mFileName;

    /**
     * 不需要外部创建实例 *
     */
    private SPManager(@NonNull String fileName) {
        this.mFileName = fileName;
    }

    /**
     * 得到SPUtils的单例
     *
     * @return SPManager
     */
    public static SPManager get(@NonNull String fileName) {
        return new SPManager(fileName);
    }

    /**
     * 得到SharedPreferences
     *
     * @return sp
     */
    private synchronized SharedPreferences getPreferences() {
        if (null == mSP) {
            mSP = App.INSTANCE.getContext().getSharedPreferences(mFileName, Context.MODE_PRIVATE);
        }
        return mSP;
    }

    /**
     * clear:清空SP. <br/>
     */
    public synchronized void clear() {
        getPreferences().edit().clear().apply();
    }

    /**
     * 把指定的String-String键值对放入SP中
     *
     * @param key   key
     * @param value value
     */
    public synchronized void putString(String key, String value) {
        getPreferences().edit().putString(key, value).apply();
    }

    /**
     * 得到SP中指定Key的String值
     *
     * @param key          key
     * @param defaultValue 默认值
     * @return value
     */
    public String getString(String key, String defaultValue) {
        return getPreferences().getString(key, defaultValue);
    }

    /**
     * 把指定的String-int键值对放入SP中
     *
     * @param key   key
     * @param value value
     */
    public synchronized void putInt(String key, int value) {
        getPreferences().edit().putInt(key, value).apply();
    }

    /**
     * 得到SP中指定Key的int值
     *
     * @param key          key
     * @param defaultValue 默认值
     * @return value
     */
    public int getInt(String key, int defaultValue) {
        return getPreferences().getInt(key, defaultValue);
    }

    /**
     * 把指定的String-long键值对放入SP中
     *
     * @param key   key
     * @param value value
     */
    public synchronized void putLong(String key, long value) {
        getPreferences().edit().putLong(key, value).apply();
    }

    /**
     * 得到SP中指定Key的long值
     *
     * @param key          key
     * @param defaultValue 默认值
     * @return value
     */
    public long getLong(String key, long defaultValue) {
        return getPreferences().getLong(key, defaultValue);
    }

    /**
     * 把指定的String-float键值对放入SP中
     *
     * @param key   key
     * @param value value
     */
    public synchronized void putFloat(String key, float value) {
        getPreferences().edit().putFloat(key, value).apply();
    }

    /**
     * 得到SP中指定Key的float值
     *
     * @param key          key
     * @param defaultValue 默认值
     * @return value
     */
    public float getFloat(String key, float defaultValue) {
        return getPreferences().getFloat(key, defaultValue);
    }

    /**
     * 把指定的String-Set<String>键值对放入SP中
     *
     * @param key    key
     * @param values values
     */
    public synchronized void putStringSet(String key, Set<String> values) {
        getPreferences().edit().putStringSet(key, values).apply();
    }

    /**
     * 得到SP中指定Key的Set<String>值
     *
     * @param key           key
     * @param defaultValues 默认值
     * @return values
     */
    public Set<String> getStringSet(String key, Set<String> defaultValues) {
        return getPreferences().getStringSet(key, defaultValues);
    }

    /**
     * 把指定的String-boolean键值对放入SP中
     *
     * @param key   key
     * @param value value
     */
    public synchronized void putBoolean(String key, boolean value) {
        getPreferences().edit().putBoolean(key, value).apply();
    }

    /**
     * 得到SP中指定Key的boolean值
     *
     * @param key          key
     * @param defaultValue 默认值
     * @return boolean
     */
    public boolean getBoolean(String key, boolean defaultValue) {
        return getPreferences().getBoolean(key, defaultValue);
    }

    /**
     * 删除指定key的数据
     *
     * @param key key
     */
    public void remove(String key) {
        getPreferences().edit().remove(key).apply();
    }
}