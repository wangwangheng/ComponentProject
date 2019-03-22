package com.xinye.core.toast;

import android.support.annotation.IntDef;
import android.support.annotation.StringRes;
import android.view.Gravity;
import android.widget.Toast;
import com.xinye.core.App;
import com.xinye.core.utils.text.StringUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * Toast工具类
 *
 * @author wangheng
 */
public class ToastUtils {

    public static final int LENGTH_SHORT = Toast.LENGTH_SHORT;
    public static final int LENGTH_LONG = Toast.LENGTH_LONG;


    private static void toast(@StringRes int stringRes, @Duration int duration) {
        toast(App.INSTANCE.getString(stringRes),duration);
    }

    private static void toast(String text, @Duration int duration) {
        if(StringUtils.isNullOrEmpty(text)){
            return;
        }
        int d = Toast.LENGTH_SHORT;
        if (duration == LENGTH_LONG) {
            d = Toast.LENGTH_LONG;
        }
        Toast toast = Toast.makeText(App.INSTANCE.getContext(), text, d);
        toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL,0,0);
        toast.show();
    }

    public static void toastShort(@StringRes int stringRes){
        toast(stringRes,LENGTH_SHORT);
    }
    public static void toastLong(@StringRes int stringRes){
        toast(stringRes,LENGTH_LONG);
    }

    public static void toastShort(String text){
        toast(text,LENGTH_SHORT);
    }
    public static void toastLong(String text){
        toast(text,LENGTH_LONG);
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({LENGTH_SHORT, LENGTH_LONG})
    @interface Duration {

    }

}
